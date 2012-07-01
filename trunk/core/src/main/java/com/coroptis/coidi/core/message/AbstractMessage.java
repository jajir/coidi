/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.core.message;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;

public abstract class AbstractMessage {

	private final Logger logger = Logger.getLogger(AbstractMessage.class);

	public static final String OPENID = "openid.";

	public static final String OPENID_NS = "ns";

	public final static String OPENID_NS_20 = "http://specs.openid.net/auth/2.0";

	public final static String MODE = "mode";

	public final static String MODE_ASSOCIATE = "associate";

	public final static String MODE_CHECKID_SETUP = "checkid_setup";

	public final static String MODE_CHECKID_IMMEDIATE = "checkid_immediate";

	private Map<String, String> map;

	private boolean isUrl;

	public abstract String getMessage();

	AbstractMessage() {
		map = new HashMap<String, String>();
	}

	public AbstractMessage(final Map<String, String> map, final String prefix) {
		this();
		if (prefix == null) {
			this.map.putAll(map);
		} else {
			for (Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().startsWith(prefix)) {
					this.map.put(entry.getKey().substring(prefix.length()),
							entry.getValue());
				} else {
					this.map.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	protected String getPrefixedMessage(final String keyPrefix) {
		try {
			if (isUrl()) {
				StringBuilder buff = concatEntries(keyPrefix, "=", "&");
				String returnTo = get("go_to");
				if (returnTo == null) {
					logger.info("There is no 'go_to' parameter in message '"
							+ buff.toString() + "'");
				} else {
					if (returnTo.contains("?")) {
						buff.insert(0, "&");
					} else {
						buff.insert(0, "?");
					}
					buff.insert(0, returnTo);
				}
				return buff.toString();
			} else {
				return concatEntries(keyPrefix, ":", "\n").toString();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

	protected StringBuilder concatEntries(final String keyPrefix,
			final String separator, final String lineEnds)
			throws UnsupportedEncodingException {
		StringBuilder buff = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			if (keyPrefix != null) {
				buff.append(keyPrefix);
			}
			buff.append(entry.getKey());
			buff.append(separator);
			if (isUrl) {
				if (entry.getValue() != null) {
					buff.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				}
			} else {
				if (entry.getValue() != null) {
					buff.append(entry.getValue());
				}
			}
			buff.append(lineEnds);
		}
		buff = buff.replace(buff.length() - lineEnds.length(), buff.length(),
				"");
		return buff;
	}

	protected Map<String, String> getMap() {
		HashMap<String, String> out = new HashMap<String, String>();
		out.putAll(map);
		return out;
	}

	public void put(final String key, final String value) {
		map.put(key, value);
	}

	public boolean putIgnoreEmpty(final String key, final String value) {
		if (value != null && value.length() > 0) {
			map.put(key, value);
			return true;
		} else {
			return false;
		}

	}

	public String get(final String key) {
		return map.get(key);
	}

	public String getNameSpace() {
		return get(OPENID_NS);
	}

	public void setNameSpace(final String nameSpace) {
		put(OPENID_NS, nameSpace);
	}

	public String getMode() {
		return get(MODE);
	}

	public void setMode(final String mode) {
		put(MODE, mode);
	}

	/**
	 * @return the isUrl
	 */
	public boolean isUrl() {
		return isUrl;
	}

	/**
	 * @param isUrl
	 *            the isUrl to set
	 */
	public void setUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}

	public byte[] convertToBytes(String s) {
		return Base64.decodeBase64(s.getBytes());
	}

	public String convertToString(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	public String convertToString(BigInteger b) {
		return convertToString(b.toByteArray());
	}

	public BigInteger convertToBigIntegerFromString(String s) {
		return new BigInteger(convertToBytes(s));
	}

}

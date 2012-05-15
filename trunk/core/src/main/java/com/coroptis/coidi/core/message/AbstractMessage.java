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

	protected Map<String, String> map;

	private boolean isUrl;

	public abstract String getMessage();

	AbstractMessage() {
		map = new HashMap<String, String>();
	}

	public AbstractMessage(final Map<String, String> map) {
		this();
		this.map.putAll(map);
	}

	protected String getPrefixedMessage(final String keyPrefix) {
		try {
			if (isUrl()) {
				StringBuilder buff = concatEntries(keyPrefix, "=", "&");
				String returnTo = get("go_to");
				if (returnTo.contains("?")) {
					buff.insert(0, "&");
				} else {
					buff.insert(0, "?");
				}
				buff.insert(0, returnTo);
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
//			if (!isUrl() || !"return_to".equals(entry.getKey())) {
				if (keyPrefix != null) {
					buff.append(keyPrefix);
				}
				buff.append(entry.getKey());
				buff.append(separator);
				if (isUrl) {
					buff.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} else {
					buff.append(entry.getValue());
				}
				buff.append(lineEnds);
//			}
		}
		buff = buff.replace(buff.length() - lineEnds.length(), buff.length(),
				"");
		return buff;
	}

	public void put(final String key, final String value) {
		map.put(key, value);
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

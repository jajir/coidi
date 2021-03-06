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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;

public abstract class AbstractMessage {

    private final Logger logger = LoggerFactory.getLogger(AbstractMessage.class);

    public static final String OPENID = "openid.";

    public static final String OPENID_NS = "ns";

    /**
     * Value for field 'openid.ns'. It identify protocol version 1.0
     */
    public final static String OPENID_NS_10 = "http://openid.net/signon/1.0";

    /**
     * Value for field 'openid.ns'. It identify protocol version 1.1
     */
    public final static String OPENID_NS_11 = "http://openid.net/signon/1.1";

    /**
     * Value for field 'openid.ns'. It identify protocol version 2.0
     */
    public final static String OPENID_NS_20 = "http://specs.openid.net/auth/2.0";

    public final static String MODE = "mode";

    public final static String MODE_ASSOCIATE = "associate";

    public final static String MODE_CHECKID_SETUP = "checkid_setup";

    public final static String MODE_CHECKID_IMMEDIATE = "checkid_immediate";

    private Map<String, String> map;

    /**
     * it's tru when message could be used in indirect communication (HTTP
     * redirect).
     */
    private boolean isUrl;

    /**
     * It should return messages that could be used in direct communication. It
     * should be always implemented.
     * 
     * @return whole OpenID message
     */
    public abstract String getMessage();

    /**
     * Return URL string that could be used for indirect communication.
     * 
     * @param targetUrl
     *            required URL to which will be user's browser redirected
     * @return url
     */
    public String getUrl(final String targetUrl) {
	throw new CoidiException(
		"indirect communication message is not supported" + " for this type of message");
    }

    AbstractMessage() {
	map = new HashMap<String, String>();
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof AbstractMessage)) {
	    return false;
	}
	AbstractMessage am = (AbstractMessage) obj;
	if (isUrl != am.isUrl) {
	    return false;
	}
	if (map.size() != am.map.size()) {
	    return false;
	}
	for (Entry<String, String> entry : map.entrySet()) {
	    if (entry.getValue() == null) {
		if (am.get(entry.getKey()) != null) {
		    return false;
		}
	    } else {
		if (!entry.getValue().equals(am.get(entry.getKey()))) {
		    return false;
		}
	    }

	}
	return true;
    }

    public boolean containsKey(final String key) {
	return map.containsKey(key);
    }

    public AbstractMessage(final Map<String, String> map, final String prefix) {
	this();
	if (prefix == null) {
	    this.map.putAll(map);
	} else {
	    for (Entry<String, String> entry : map.entrySet()) {
		if (entry.getKey().startsWith(prefix)) {
		    this.map.put(entry.getKey().substring(prefix.length()), entry.getValue());
		} else {
		    this.map.put(entry.getKey(), entry.getValue());
		}
	    }
	}
    }

    protected String getUrlMessage(final String keyPrefix, final String targetUrl) {
	try {
	    if (isUrl()) {
		StringBuilder buff = concatEntries(keyPrefix, "=", "&");
		if (targetUrl == null) {
		    logger.warn("targetUrl method return null!");
		} else {
		    if (targetUrl.contains("?")) {
			buff.insert(0, "&");
		    } else {
			buff.insert(0, "?");
		    }
		    buff.insert(0, targetUrl);
		}
		return buff.toString();
	    } else {
		throw new CoidiException("it's not Url message type, call different method.");
	    }
	} catch (UnsupportedEncodingException e) {
	    logger.error(e.getMessage(), e);
	    throw new CoidiException(e.getMessage(), e);
	}
    }

    protected String getPrefixedMessage(final String keyPrefix) {
	try {
	    return concatEntries(keyPrefix, ":", "\n").toString();
	} catch (UnsupportedEncodingException e) {
	    logger.error(e.getMessage(), e);
	    throw new CoidiException(e.getMessage(), e);
	}
    }

    protected StringBuilder concatEntries(final String keyPrefix, final String separator,
	    final String lineEnds) throws UnsupportedEncodingException {
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
	buff = buff.replace(buff.length() - lineEnds.length(), buff.length(), "");
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
    public void setUrl(final boolean isUrl) {
	this.isUrl = isUrl;
    }

    public static byte[] convertToBytes(final String s) {
	return Base64.decodeBase64(s.getBytes());
    }

    public String convertToString(final byte[] b) {
	return new String(Base64.encodeBase64(b));
    }

    public String convertToString(final BigInteger b) {
	return convertToString(b.toByteArray());
    }

    public static BigInteger convertToBigIntegerFromString(final String s) {
	return new BigInteger(convertToBytes(s));
    }

}

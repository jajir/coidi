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
package com.coroptis.coidi.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.services.MessageService;
import com.google.common.base.Preconditions;

public class MessageServiceImpl implements MessageService {

    private Logger logger = LoggerFactory.getLogger(CryptographyServiceImpl.class);

    @Override
    public Map<String, String> convertUrlToMap(final String query) {
	Preconditions.checkNotNull(query, "query");
	Map<String, String> map = new HashMap<String, String>();
	try {
	    for (String tuple : query.split("&")) {
		int equals = tuple.indexOf('=');
		if (equals <= 0) {
		    //throw new CoidiException("invalid URL format '" + tuple + "'.");
			//Parameter without equal is used in wicket for marking page version so exception is not wanted, just skip
			continue;
		}
		map.put(URLDecoder.decode(tuple.substring(0, equals), "UTF-8"),
			URLDecoder.decode(tuple.substring(equals + 1), "UTF-8"));
	    }
	} catch (UnsupportedEncodingException e) {
	    logger.error(e.getMessage(), e);
	    throw new CoidiException(e.getMessage(), e);
	}
	return map;
    }

    @Override
    public String extractStringForSign(final AbstractMessage response, final String prefix) {
	StringBuilder buff = new StringBuilder(1024);
	String signed = response.get("signed");
	for (String item : signed.split(",")) {
	    buff.append(item);
	    buff.append(':');
	    String value = response.get(getKey(prefix, item));
	    if (value != null) {
		buff.append(value);
	    }
	    buff.append('\n');
	}
	return buff.toString();
    }

    private String getKey(final String prefix, final String simpleKey) {
	if (prefix == null) {
	    return simpleKey;
	} else {
	    return prefix + simpleKey;
	}
    }

}

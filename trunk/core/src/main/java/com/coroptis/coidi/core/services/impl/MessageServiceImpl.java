package com.coroptis.coidi.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.services.MessageService;
import com.google.common.base.Preconditions;

public class MessageServiceImpl implements MessageService {

	@Inject
	private Logger logger;

	@Override
	public Map<String, String> convertUrlToMap(final String query) {
		Preconditions.checkNotNull(query, "query");
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String tuple : query.split("&")) {
				int equals = tuple.indexOf('=');
				if (equals <= 0) {
					throw new CoidiException("invalid URL format '" + tuple
							+ "'.");
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
	public String extractStringForSign(final AbstractMessage response,
			final String prefix) {
		StringBuilder buff = new StringBuilder();
		String signed = response.get("signed");
		for (String item : signed.split(",")) {
			buff.append(response.get(getKey(prefix, item)));
			buff.append(",");
		}
		return buff.substring(0, buff.length() - 1).toString();
	}

	private String getKey(final String prefix, final String simpleKey) {
		if (prefix == null) {
			return simpleKey;
		} else {
			return prefix + simpleKey;
		}
	}

}

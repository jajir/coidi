package com.coroptis.coidi.op.view.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.view.services.MessageService;
import com.google.common.base.Preconditions;

public class MessageServiceImpl implements MessageService {

	private final Logger logger = Logger.getLogger(MessageServiceImpl.class);

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
				System.out.println(tuple);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
		return map;
	}

}

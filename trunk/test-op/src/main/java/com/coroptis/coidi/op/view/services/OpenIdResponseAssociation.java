package com.coroptis.coidi.op.view.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;

public class OpenIdResponseAssociation extends AbstractOpenIdResponse {

	private final Logger logger = Logger
			.getLogger(OpenIdResponseAssociation.class);

	private final Map<String, String> map = new HashMap<String, String>();

	public void put(final String key, final String value) {
		map.put(key, value);
	}

	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		try {
			for (Entry<String, String> entry : map.entrySet()) {
				sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				sb.append(":");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				sb.append("\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}
}

package com.coroptis.coidi.core.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OpenIdResponseAssociation extends AbstractOpenIdResponse {

	
	
	
	private final Map<String, String> map = new HashMap<String, String>();

	public void put(final String key, final String value) {
		map.put(key, value);
	}

	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		// try {
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			// sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			sb.append(":");
			// sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			sb.append(entry.getValue());
			sb.append("\n");
		}
		return sb.toString();
		// } catch (UnsupportedEncodingException e) {
		// logger.error(e.getMessage(), e);
		// throw new CoidiException(e.getMessage(), e);
		// }
	}
}

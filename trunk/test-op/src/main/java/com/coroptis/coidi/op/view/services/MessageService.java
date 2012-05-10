package com.coroptis.coidi.op.view.services;

import java.util.Map;

public interface MessageService {

	/**
	 * Convert incoming message to map.
	 * 
	 * @param query
	 * @return
	 */
	Map<String, String> convertUrlToMap(String query);

}

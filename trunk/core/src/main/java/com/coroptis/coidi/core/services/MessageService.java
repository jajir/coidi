package com.coroptis.coidi.core.services;

import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;

public interface MessageService {

	/**
	 * Convert incoming message to map.
	 * 
	 * @param query
	 * @return
	 */
	Map<String, String> convertUrlToMap(String query);

	String extractStringForSign(final AbstractMessage response,
			final String prefix);

}

package com.coroptis.coidi.op.services;

import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AbstractOpenIdResponse;

/**
 * Allows to process one specific openID message.
 * 
 * @author jan
 * 
 */
public interface OpenIdDispatcher {

	final static String OPENID_MODE = AbstractMessage.OPENID
			+ AbstractMessage.MODE;

	/**
	 * Process message. If return value is <code>null</code> then next openID
	 * request dispatcher is used. It will be used in chain of command patter.
	 * 
	 * @param requestParams
	 * @return
	 */
	AbstractOpenIdResponse process(Map<String, String> requestParams);

}

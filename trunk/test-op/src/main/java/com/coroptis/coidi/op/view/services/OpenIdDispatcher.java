package com.coroptis.coidi.op.view.services;

import java.util.Map;

/**
 * Allows to process one specific openID message.
 * 
 * @author jan
 * 
 */
public interface OpenIdDispatcher {

	final static String MODE = "openid.mode";

	final static String MODE_ASSOCIATE = "associate";

	final static String MODE_CHECKID_SETUP = "checkid_setup";

	final static String MODE_CHECKID_IMMEDIATE = "checkid_immediate";

	final static String OPENID_NS = "openid.ns";

	final static String OPENID_NS_20 = "http://specs.openid.net/auth/2.0";

	/**
	 * Process message. If return value is <code>null</code> then next openID
	 * request dispatcher is used. It will be used in chain of command patter.
	 * 
	 * @param requestParams
	 * @return
	 */
	AbstractOpenIdResponse process(Map<String, String> requestParams);

}

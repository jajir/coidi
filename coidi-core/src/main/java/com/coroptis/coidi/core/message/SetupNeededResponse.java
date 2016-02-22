package com.coroptis.coidi.core.message;

/**
 * It's negative response to checkid_immediate authentication request.
 * 
 * @author jan
 *
 */
public class SetupNeededResponse extends AbstractOpenIdResponse {

    public final static String MODE_SETUP_NEEDED = "setup_needed";

    public SetupNeededResponse(final String nameSpace) {
	super();
	setUrl(true);
	setNameSpace(nameSpace);
	setMode(MODE_SETUP_NEEDED);
    }

}

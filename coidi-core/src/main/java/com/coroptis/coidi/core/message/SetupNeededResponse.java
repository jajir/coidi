package com.coroptis.coidi.core.message;

import com.google.common.base.Preconditions;

/**
 * It's negative response to checkid_immediate authentication request.
 * 
 * @author jan
 *
 */
public class SetupNeededResponse extends AbstractOpenIdResponse {

	public final static String MODE_SETUP_NEEDED = "setup_needed";

	private final String returnToUrl;

	public SetupNeededResponse(final String nameSpace, final String returnToUrl) {
		super();
		setUrl(true);
		setNameSpace(nameSpace);
		setMode(MODE_SETUP_NEEDED);
		this.returnToUrl=Preconditions.checkNotNull(returnToUrl);
	}

	@Override
    public String getUrl(final String targetUrl) {
    	return getUrl();
    }

	
	public String getUrl() {
		return getUrlMessage(OPENID, returnToUrl);
	}

}

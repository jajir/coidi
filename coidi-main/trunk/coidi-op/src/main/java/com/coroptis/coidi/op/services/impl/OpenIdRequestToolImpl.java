package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestTool;

/**
 * Implementation
 * 
 * @author jirout
 * 
 */
public class OpenIdRequestToolImpl implements OpenIdRequestTool {

    @Override
    public boolean isOpenIdVersion20(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) != null
		&& AbstractMessage.OPENID_NS_20.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS));
    }

    @Override
    public boolean isOpenIdVersion1x(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) == null
		|| AbstractMessage.OPENID_NS_10.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS))
		|| AbstractMessage.OPENID_NS_11.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS));
    }

}

package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;

import com.coroptis.coidi.conf.util.AuthenticationRequest;
import com.coroptis.coidi.op.view.services.AbstractOpenIdResponse;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;

public class OpenidDispatcherAuthentication implements OpenIdDispatcher {

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(MODE).equals(MODE_CHECKID_IMMEDIATE)) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest();
			
		}
		return null;
	}
}

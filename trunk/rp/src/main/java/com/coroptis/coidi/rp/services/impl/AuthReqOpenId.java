package com.coroptis.coidi.rp.services.impl;

import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthReqOpenId implements AuthReq {

	@Override
	public boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());
		return false;
	}

}

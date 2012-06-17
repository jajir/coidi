package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthReqOpenId implements AuthReq {

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());
		return false;
	}

}

package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;

public class AuthReqOpenId implements AuthReq {

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		authenticationRequest.setRealm("not in use");
		authenticationRequest.setReturnTo("http://localhost:8081/");
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());
		return false;
	}

}

package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;

public interface AuthReq {

	boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult);

}

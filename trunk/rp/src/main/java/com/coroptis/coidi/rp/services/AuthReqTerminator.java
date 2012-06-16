package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * This is last command in row.
 * 
 * @author jan
 * 
 */
public class AuthReqTerminator implements AuthReq {

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		return true;
	}

}

package com.coroptis.coidi.rp.services.impl;

import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * This is last command in row.
 * 
 * @author jan
 * 
 */
public class AuthReqTerminator implements AuthReq {

	@Override
	public boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		return true;
	}

}

package com.coroptis.coidi.rp.services;

import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
/**
 * 
 * @author jan
 *
 */
public interface AuthReq {

	/**
	 * 
	 * @param authenticationRequest
	 * @param discoveryResult
	 * @param parameters
	 * @return
	 */
	boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters);

}

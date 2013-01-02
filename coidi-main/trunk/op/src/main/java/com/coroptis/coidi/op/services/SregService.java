package com.coroptis.coidi.op.services;

import java.util.Set;

import com.coroptis.coidi.core.message.AuthenticationRequest;

/**
 * Service helps process OpenID simple registration extension.
 * 
 * @author jirout
 * 
 */
public interface SregService {

	/**
	 * Treating to required and optional fields is same so both could be
	 * processed in one set.
	 * 
	 * @param authenticationRequest
	 *            required {@link AuthenticationRequest}
	 * @return
	 */
	Set<String> extractRequestedKeys(AuthenticationRequest authenticationRequest);

}

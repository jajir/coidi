package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;

public interface AuthenticationService {

	/**
	 * Return true if it's valid authentication request. It's when it have mode
	 * checkid_setup or checkid_immediate and contains claimed_id or identity.
	 * 
	 * @param authenticationRequest
	 *            required {@link AuthenticationRequest} object
	 * @return true when it's valid authentication request otherwise return
	 *         false
	 */
	boolean isAuthenticationRequest(AuthenticationRequest authenticationRequest);

	AuthenticationResponse process(AuthenticationRequest authenticationRequest);
}

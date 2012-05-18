package com.coroptis.coidi.rp.view.services;

import com.coroptis.coidi.core.message.AuthenticationResponse;

public interface AuthenticationService {

	public final static Integer NONCE_EXPIRATION_TIME_IN_MINUTES = 30;

	Boolean verify(AuthenticationResponse authenticationResponse);

}

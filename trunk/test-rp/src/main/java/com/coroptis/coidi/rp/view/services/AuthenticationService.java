package com.coroptis.coidi.rp.view.services;

import com.coroptis.coidi.core.message.AuthenticationResponse;

public interface AuthenticationService {
	
	Boolean verify(AuthenticationResponse authenticationResponse);
	
}

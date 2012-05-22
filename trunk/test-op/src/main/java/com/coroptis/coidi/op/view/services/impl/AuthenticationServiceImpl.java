package com.coroptis.coidi.op.view.services.impl;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.view.services.AuthenticationService;
import com.google.common.base.Preconditions;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public boolean isAuthenticationRequest(
			final AuthenticationRequest authenticationRequest) {
		Preconditions.checkNotNull(authenticationRequest,
				"authenticationRequest");
		if (AuthenticationRequest.MODE_CHECKID_SETUP
				.equals(authenticationRequest.getMode())
				|| AuthenticationRequest.MODE_CHECKID_IMMEDIATE
						.equals(authenticationRequest.getMode())) {
			return authenticationRequest.getIdentity() != null
					|| authenticationRequest.getClaimedId() != null;
		}
		return false;
	}

}

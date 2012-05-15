package com.coroptis.coidi.rp.view.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.rp.view.services.AuthenticationService;
import com.coroptis.coidi.rp.view.services.NonceService;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Inject
	private NonceService nonceService;

	@Override
	public Boolean verify(AuthenticationResponse authenticationResponse) {
		if (authenticationResponse.getMode().equals("id_res")) {
			return true;
		}
		return false;
	}
}

package com.coroptis.coidi.op.view.services.impl;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.UserVerifier;

public class UserVerifierImpl implements UserVerifier {

	@Override
	public boolean verify(String opLocalIdentity, HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserLogged(HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void storeAuthenticatonRequest(HttpSession session, AuthenticationRequest authenticationRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSelectedOpLocalIdentifier(HttpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

}

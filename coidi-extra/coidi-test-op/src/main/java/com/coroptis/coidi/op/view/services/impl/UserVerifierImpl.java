package com.coroptis.coidi.op.view.services.impl;

import javax.servlet.http.HttpSession;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.UserVerifier;
import com.coroptis.coidi.op.view.utils.UserSession;

public class UserVerifierImpl implements UserVerifier {

    @Inject
    private ApplicationStateManager asm;

    private UserSession getUserSession(){
	return asm.get(UserSession.class);
    }
    
    @Override
    public boolean verify(String opLocalIdentity, HttpSession session) {
	for (final Identity ident : getUserSession().getUser().getIdentities()) {
	    if (opLocalIdentity.equals(ident.getIdIdentity())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean isUserLogged(HttpSession session) {
	return getUserSession().isLogged();
    }

    @Override
    public void storeAuthenticatonRequest(HttpSession session,
	    AuthenticationRequest authenticationRequest) {
	getUserSession().setAuthenticationRequest(authenticationRequest);
    }

    @Override
    public String getSelectedOpLocalIdentifier(HttpSession session) {
	return getUserSession().getLoggedIdentity();
    }

}

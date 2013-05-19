package com.coroptis.coidi.op.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationImmediateProcessor;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.util.OpenId20;

public class AuthenticationImmediateProcessorImpl implements AuthenticationImmediateProcessor {

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    @OpenId20
    private AuthenticationProcessor authenticationProcessor;

    @Inject
    private IdentityService identityService;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    UserSessionSkeleton userSession) {
	if (!authenticationService.isAuthenticationRequest(authenticationRequest)) {
	    return negativeResponseGenerator
		    .simpleError("authentication request doesn't contains any idenity field");
	}

	Identity identity = identityService.getByOpLocalIdentifier(authenticationRequest
		.getIdentity());
	if (identity == null) {
	    return negativeResponseGenerator.simpleError("There is no such identity '"
		    + authenticationRequest.getIdentity() + "'");
	}

	if (!identityService.isIdentityLogged(userSession, identity)) {
	    return negativeResponseGenerator.simpleError("Idenity '"
		    + authenticationRequest.getIdentity() + "' is not logged in");
	}

	AuthenticationResponse response = new AuthenticationResponse();
	Set<String> fieldToSign = new HashSet<String>();
	AbstractMessage out = authenticationProcessor.process(authenticationRequest, response,
		identity, fieldToSign);
	return out;
    }

}

package com.coroptis.coidi.op.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationSetupProcessor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.util.OpenId20;

public class AuthenticationSetupProcessorImpl implements AuthenticationSetupProcessor {

    @Inject
    private Logger logger;

    @Inject
    private IdentityService identityService;

    @Inject
    @OpenId20
    private AuthenticationProcessor authenticationProcessor;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final UserSessionSkeleton userSession) {

	if (!userSession.isLogged()) {
	    logger.debug("User is not logged in.");
	    userSession.setAuthenticationRequest(authenticationRequest);
	    return negativeResponseGenerator.applicationError("User is not logged in",
		    NegativeResponseGenerator.APPLICATION_ERROR_PLEASE_LOGIN);
	}

	Identity identity = identityService.getByOpLocalIdentifier(authenticationRequest
		.getIdentity());

	Set<String> fieldToSign = new HashSet<String>();
	return authenticationProcessor.process(authenticationRequest, new AuthenticationResponse(),
		identity, userSession, fieldToSign);
    }

}

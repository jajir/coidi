package com.coroptis.coidi.op.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.AuthenticationSetupProcessor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.util.OpenId20;

public class AuthenticationSetupProcessorImpl implements AuthenticationSetupProcessor {

    @Inject
    private Logger logger;

    @Inject
    private AuthenticationService authenticationService;

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
	if (!authenticationService.isAuthenticationRequest(authenticationRequest)) {
	    logger.debug("authentication request doesn't contains any idenity field");
	    return negativeResponseGenerator
		    .simpleError("authentication request doesn't contains any idenity field");
	}

	// TODO should be perform just when all information are valid
	if (!userSession.isLogged()) {
	    logger.debug("User is not logged in.");
	    userSession.setAuthenticationRequest(authenticationRequest);
	    return negativeResponseGenerator.applicationError("User is not logged in",
		    NegativeResponseGenerator.APPLICATION_ERROR_PLEASE_LOGIN);
	}

	if (AuthenticationRequest.IDENTITY_SELECT.equals(authenticationRequest.getIdentity())) {
	    if (StringUtils.isEmpty(authenticationRequest.getSelectedIdentity())) {
		return negativeResponseGenerator.applicationError("requested identity is '"
			+ AuthenticationRequest.IDENTITY_SELECT
			+ "' but user didin't put selected identity in property '"
			+ AuthenticationRequest.USERS_SELECTED_IDENTITY + "'",
			NegativeResponseGenerator.APPLICATION_ERROR_SELECT_IDENTITY);
	    } else {
		authenticationRequest.setIdentity(authenticationRequest.getSelectedIdentity());
		if (AuthenticationRequest.IDENTITY_SELECT.equals(authenticationRequest
			.getClaimedId())) {
		    logger.warn("authentication request contains '"
			    + AuthenticationRequest.IDENTITY_SELECT + "' in '"
			    + AuthenticationRequest.CLAIMED_ID + "'");
		    authenticationRequest.setClaimedId(authenticationRequest.getSelectedIdentity());
		}
	    }
	}

	Identity identity = identityService.getByOpLocalIdentifier(authenticationRequest
		.getIdentity());

	if (identity == null) {
	    logger.debug("Requested identity '" + authenticationRequest.getIdentity()
		    + "' doesn't exists.");
	    return identityBelongsToOtherUser(authenticationRequest.getIdentity(),
		    userSession.getIdUser());
	}

	if (identityService.isUsersOpIdentifier(userSession.getIdUser(),
		authenticationRequest.getIdentity())) {
	    Set<String> fieldToSign = new HashSet<String>();
	    return authenticationProcessor.process(authenticationRequest,
		    new AuthenticationResponse(), identity, fieldToSign);
	} else {
	    return identityBelongsToOtherUser(authenticationRequest.getIdentity(),
		    userSession.getIdUser());
	}
    }

    private ErrorResponse identityBelongsToOtherUser(final String identity, final Integer idUser) {
	return negativeResponseGenerator.simpleError("Identity '" + identity
		+ "' doesn't belongs to user '" + idUser + "'.");
    }

}

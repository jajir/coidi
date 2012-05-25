package com.coroptis.coidi.op.view.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.view.entities.StatelessModeNonce;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.AuthenticationService;
import com.coroptis.coidi.op.view.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private AssociationService associationService;

	@Inject
	private SigningService signingService;

	@Inject
	private StatelessModeNonceService statelessModeNonceService;

	@Inject
	@Symbol("op.server")
	private String opServer;

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

	public AbstractOpenIdResponse process(
			AuthenticationRequest authenticationRequest) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setIdentity(authenticationRequest.getIdentity());
		response.setNonce(nonceService.createNonce());
		response.setReturnTo(authenticationRequest.getReturnTo());
		response.put("go_to", authenticationRequest.getReturnTo());
		response.setOpEndpoint(opServer + "openid");
		if (authenticationRequest.getAssocHandle() == null) {
			// state less mode
			StatelessModeNonce statelessModeNonce = statelessModeNonceService
					.createStatelessModeNonce(response.getNonce());
			response.setSigned("identity,nonce,return_to");
			response.setSignature(signingService.sign(response,
					statelessModeNonce.getMacKey()));
		} else {
			Association association = associationService
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			if (association == null) {
				return new ErrorResponse(false, "Invalid assoc handle '"
						+ authenticationRequest.getAssocHandle()
						+ "', associaction wasn't associated.");
			}
			response.setAssocHandle(authenticationRequest.getAssocHandle());
			response.setSigned("assoc_handle,identity,nonce,return_to");
			response.setSignature(signingService.sign(response, association));
		}
		logger.debug("authentication response: " + response.getMessage());
		return response;
	}
}
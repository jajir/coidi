package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
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
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.services.StatelessModeNonceService;
import com.coroptis.coidi.op.view.utils.UserSession;

public class OpenidDispatcherAuthenticationSetup implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private AssociationService associationService;

	@Inject
	private SigningService signingService;

	@Inject
	private AuthenticationService authenticationService;

	@Inject
	private ApplicationStateManager applicationStateManager;

	@Inject
	private StatelessModeNonceService statelessModeNonceService;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(OPENID_MODE).equals(
				AuthenticationRequest.MODE_CHECKID_SETUP)) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest(
					requestParams);
			if (!authenticationService
					.isAuthenticationRequest(authenticationRequest)) {
				logger.debug("authentication request doesn't contains any idenity field");
				return null;
			}

			if (!applicationStateManager.exists(UserSession.class)) {
				applicationStateManager.set(UserSession.class,
						new UserSession());
			}
			UserSession userSession = applicationStateManager
					.get(UserSession.class);
			if (!userSession.isLogged()) {
				userSession.setAuthenticationRequest(authenticationRequest);
				return new AbstractOpenIdResponse() {
					@Override
					public boolean isUrl() {
						return true;
					}

					@Override
					public String getMessage() {
						return "./login";
					}
				};
			}

			Association association = associationService
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			if (authenticationRequest.getAssocHandle() != null
					&& association == null) {
				return new ErrorResponse(false, "Invalid assoc handle '"
						+ authenticationRequest.getAssocHandle()
						+ "', associaction wasn't associated.");
			}

			AuthenticationResponse response = new AuthenticationResponse();
			response.setNonce(nonceService.createNonce());
			response.setIdentity(authenticationRequest.getIdentity());
			response.setReturnTo(authenticationRequest.getReturnTo());

			if (association == null) {
				// state less mode
				StatelessModeNonce statelessModeNonce = statelessModeNonceService
						.createStatelessModeNonce(response.getNonce());
				response.setSigned("identity,nonce,return_to");
				response.setSignature(signingService.sign(response,
						statelessModeNonce.getMacKey()));
			} else {
				response.setSigned("assoc_handle,identity,nonce,return_to");
				response.setAssocHandle(authenticationRequest.getAssocHandle());
				response.setSignature(signingService
						.sign(response, association));
			}
			response.put("go_to", authenticationRequest.getReturnTo());
			return response;
		}
		return null;
	}
}

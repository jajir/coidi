package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.NonceService;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;

public class OpenidDispatcherAuthenticationImmediate implements
		OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private AssociationService associationService;

	@Inject
	private SigningService signingService;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(OPENID_MODE).equals(
				AuthenticationRequest.MODE_CHECKID_IMMEDIATE)) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest(
					requestParams);

			Association association = associationService
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			if (association == null) {
				ErrorResponse errorResponse = new ErrorResponse(false);
				String msg = "Unable to find association handle '"
						+ authenticationRequest.getAssocHandle() + "'";
				logger.warn(msg);
				errorResponse.setError(msg);
				return errorResponse;
			}

			AuthenticationResponse response = new AuthenticationResponse();
			response.setAssocHandle(authenticationRequest.getAssocHandle());
			response.setIdentity(authenticationRequest.getIdentity());
			response.setNonce(nonceService.createNonce());
			response.setReturnTo(authenticationRequest.getReturnTo());
			response.setSigned("assoc_handle,identity,nonce,return_to");
			response.setSignature(signingService.sign(response, association));
			response.put("go_to", authenticationRequest.getReturnTo());
			return response;
		}
		return null;
	}

}

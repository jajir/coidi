package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.CheckAuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.StatelessModeNonceService;

/**
 * When no previous dispatcher process message then this report that message is
 * invalid.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherCheckAuthentication implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private StatelessModeNonceService statelessModeNonceService;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(OPENID_MODE).equals(
				CheckAuthenticationRequest.MODE_CHECK_AUTHENTICATION)) {
			CheckAuthenticationRequest request = new CheckAuthenticationRequest(
					requestParams);
			logger.debug("processing: " + request);
			CheckAuthenticationResponse response = new CheckAuthenticationResponse();
			response.setIsValid(true);
			if (!nonceService.verifyNonce(request.getNonce(), 30)) {
				response.setIsValid(false);
				logger.info("Invalid nonce '" + request.getNonce() + "'");
				return response;
			}
			if (!statelessModeNonceService
					.isValidCheckAuthenticationRequest(request)) {
				response.setIsValid(false);
				return response;
			}
			return response;
		}
		return null;
	}

}

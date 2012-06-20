package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.util.UserSession;

public class OpenidDispatcherAuthenticationSetup implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private AuthenticationService authenticationService;

	@Inject
	private ApplicationStateManager applicationStateManager;

	@Override
	public AbstractMessage process(Map<String, String> requestParams) {
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

			return authenticationService.process(authenticationRequest);
		}
		return null;
	}
}

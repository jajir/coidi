package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.coroptis.coidi.conf.services.MessageService;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.rp.view.services.AuthenticationService;
import com.coroptis.coidi.rp.view.util.UserSession;

public class AuthenticationResponseDispatcher implements Dispatcher {

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private Logger logger;

	@Inject
	private MessageService messageService;

	@Inject
	private AuthenticationService authenticationService;

	private final ApplicationStateManager asm;

	public AuthenticationResponseDispatcher(ApplicationStateManager asm) {
		this.asm = asm;
	}

	@Override
	public boolean dispatch(Request request, Response response)
			throws IOException {

		HttpServletRequest httpRequest = requestGlobals.getHTTPServletRequest();

		if (httpRequest.getQueryString() != null
				&& httpRequest.getQueryString().length() > 0) {
			logger.debug("query is coming: " + httpRequest.getQueryString());
			Map<String, String> map = messageService
					.convertUrlToMap(httpRequest.getQueryString());
			AuthenticationResponse authenticationResponse = new AuthenticationResponse(
					map);
			if (authenticationService.verify(authenticationResponse)) {
				UserSession session = asm.get(UserSession.class);
				session.setSsoIdentity(authenticationResponse.getIdentity());
			}
		}
		return false;
	}

}

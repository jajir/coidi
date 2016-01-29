/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.CheckAuthenticationResponse;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthenticationService;
import com.coroptis.coidi.rp.services.HttpTransportService;
import com.coroptis.coidi.rp.view.util.UserSession;

/**
 * This dispatcher works more like filter. It examine all incoming requests if
 * it's part of OpenID protocol. If it is OpenID request than it process.
 * 
 * @author jirout
 * 
 */
public class AuthenticationResponseDispatcher implements Dispatcher {

    @Inject
    private RequestGlobals requestGlobals;

    @Inject
    private Logger logger;

    @Inject
    private MessageService messageService;

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private HttpTransportService httpTransportService;

    private final ApplicationStateManager asm;

    public AuthenticationResponseDispatcher(ApplicationStateManager asm) {
	this.asm = asm;
    }

    @Override
    public boolean dispatch(final Request request, final Response response) throws IOException {

	HttpServletRequest httpRequest = requestGlobals.getHTTPServletRequest();

	if (httpRequest.getQueryString() != null && httpRequest.getQueryString().length() > 0) {
	    logger.debug("query is coming: " + httpRequest.getQueryString());
	    Map<String, String> map = messageService.convertUrlToMap(httpRequest.getQueryString());
	    AuthenticationResponse authenticationResponse = new AuthenticationResponse(map);

	    if (asm.exists(Association.class)) {
		logger.debug("there is association.");
		AuthenticationResult authenticationResult = authenticationService
			.verify(authenticationResponse, asm.get(Association.class));
		if (authenticationResult.isPositive()) {
		    UserSession session = asm.get(UserSession.class);
		    session.setAuthenticationResult(authenticationResult);
		    session.setSsoIdentity(authenticationResponse.getIdentity());
		}
	    } else {
		logger.debug("there is no association - stateless mode.");
		// TODO finish stateless mode, move it to RP library
		CheckAuthenticationRequest checkAuthenticationRequest = new CheckAuthenticationRequest();
		checkAuthenticationRequest.setIdentity(authenticationResponse.getIdentity());
		checkAuthenticationRequest
			.setInvalidateHandle(authenticationResponse.getInvalidateHandle());
		checkAuthenticationRequest.setNonce(authenticationResponse.getNonce());
		checkAuthenticationRequest.setReturnTo(authenticationResponse.getReturnTo());
		checkAuthenticationRequest.setSignature(authenticationResponse.getSignature());
		checkAuthenticationRequest.setSigned(authenticationResponse.getSigned());
		logger.debug("check authentication msg: " + checkAuthenticationRequest);

		// post it to server
		CheckAuthenticationResponse response2 = new CheckAuthenticationResponse(
			httpTransportService.doPost(authenticationResponse.getOpEndpoint(),
				checkAuthenticationRequest.getMap()));
		if (response2.getIsValid()) {
		    UserSession session = asm.get(UserSession.class);
		    session.setSsoIdentity(authenticationResponse.getIdentity());
		} else {
		    logger.error("check authentication wasn't successful");
		}
	    }

	}
	return false;
    }

}

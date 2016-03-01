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
package com.coroptis.coidi.op.services.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.google.common.base.Preconditions;

public class OpenIdDispatcherAuthenticationSetup20 implements OpenIdDispatcher {

    private final AuthProc authenticationProcessor;

     
    public OpenIdDispatcherAuthenticationSetup20(final AuthProc authenticationProcessor) {
	this.authenticationProcessor = Preconditions.checkNotNull(authenticationProcessor);
    }

    @Override
    public AbstractMessage process(Map<String, String> requestParams, HttpSession userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_SETUP)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    Set<String> fieldToSign = new HashSet<String>();
	    return authenticationProcessor.process(authenticationRequest,
		    new AuthenticationResponse(), userSession, fieldToSign);
	}
	return null;
    }

}

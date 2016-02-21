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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.util.OpenId11CheckIdImmediate;

/**
 * Process openid.more=checkid_immediate.
 * 
 * @author jirout
 * 
 */
public class OpenIdDispatcherAuthenticationImmediate11 implements OpenIdDispatcher {

    @Inject
    @OpenId11CheckIdImmediate
    private AuthProc authenticationProcessor;

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final HttpSession userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_IMMEDIATE)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    AuthenticationResponse response = new AuthenticationResponse();
	    Set<String> fieldToSign = new HashSet<String>();
	    return authenticationProcessor.process(authenticationRequest, response, userSession,
		    fieldToSign);
	}
	return null;
    }
}

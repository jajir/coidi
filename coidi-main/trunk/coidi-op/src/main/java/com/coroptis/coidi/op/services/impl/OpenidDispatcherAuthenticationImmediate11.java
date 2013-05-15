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

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.AuthenticationImmediateProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Process openid.more=checkid_immediate.
 * 
 * @author jirout
 * 
 */
public class OpenidDispatcherAuthenticationImmediate11 implements OpenIdDispatcher {

    @Inject
    private AuthenticationImmediateProcessor authenticationImmediateProcessor;

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_IMMEDIATE)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    AbstractMessage out =authenticationImmediateProcessor.process(authenticationRequest, userSession); 
	    /**
	     * Prevent returning openid.op_endpoint
	     */
	    out.put(AuthenticationResponse.OP_ENDPOINT, null);
	    return out;
	}
	return null;
    }
}

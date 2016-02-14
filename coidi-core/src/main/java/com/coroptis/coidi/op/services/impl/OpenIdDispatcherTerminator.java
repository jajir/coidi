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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * This class in terminator of chain of command. When processing of message end
 * in this class it means that no command could process request.
 * <p>
 * Class works with both OpenID protocol versions.
 * </p>
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherTerminator implements OpenIdDispatcher {

    private final NegativeResponseGenerator negativeResponseGenerator;

    @Inject
    public OpenIdDispatcherTerminator(final NegativeResponseGenerator negativeResponseGenerator) {
	this.negativeResponseGenerator = negativeResponseGenerator;
    }

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final HttpSession userSession) {
	return negativeResponseGenerator.buildError(
		"Unable to process incoming message, invalid 'openid.mode'",
		" or missing parameters or missing OpenID extension support.");
    }

}

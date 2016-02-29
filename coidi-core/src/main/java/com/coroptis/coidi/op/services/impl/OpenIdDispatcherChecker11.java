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
import com.google.common.base.Preconditions;

/**
 * Verify that openid.mode is filled.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherChecker11 implements OpenIdDispatcher {

    private final NegativeResponseGenerator negativeResponseGenerator;

    @Inject
    public OpenIdDispatcherChecker11(final NegativeResponseGenerator negativeResponseGenerator) {
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
    }

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final HttpSession userSession) {
	if (requestParams.get(OpenIdDispatcher.OPENID_MODE) == null) {
	    return negativeResponseGenerator.buildErrorWithNs(AbstractMessage.OPENID_NS_11,
		    "key value '", OpenIdDispatcher.OPENID_MODE, "' is empty");
	}
	return null;
    }

}

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
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Verify that basic message requirements are meat. This dispatched should be
 * first in chain.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherChecker implements OpenIdDispatcher {

    private final static String OPENID_NS = AbstractMessage.OPENID + AbstractMessage.OPENID_NS;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE) == null) {
	    return negativeResponseGenerator
		    .simpleError("key value '" + OPENID_MODE + "' is empty");
	}
	if (requestParams.get(OPENID_NS) == null) {
	    return negativeResponseGenerator.simpleError("key value '" + OPENID_NS + "' is empty");
	} else {
	    if (!AbstractMessage.OPENID_NS_20.equals(requestParams.get(OPENID_NS))) {
		return negativeResponseGenerator.simpleError("Unsupported OpenId namespace '"
			+ requestParams.get(OPENID_NS) + "'");
	    }
	}
	return null;
    }

}

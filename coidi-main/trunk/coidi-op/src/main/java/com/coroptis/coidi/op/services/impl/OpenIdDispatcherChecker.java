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

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Verify that basic message requirements are meat. This dispatched should be
 * first in chain.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherChecker implements OpenIdDispatcher {

	private final static Logger logger = Logger
			.getLogger(OpenIdDispatcherChecker.class);

	private final static String OPENID_NS = AbstractMessage.OPENID
			+ AbstractMessage.OPENID_NS;

	@Override
	public AbstractMessage process(Map<String, String> requestParams,
			UserSessionSkeleton userSession) {
		if (requestParams.get(OPENID_MODE) == null) {
		    //TODO move to negative response generator
			StringBuilder buff = new StringBuilder();
			buff.append("key value '");
			buff.append(OPENID_MODE);
			buff.append("' is empty");
			logger.error(buff.toString());
			ErrorResponse errorResponse = new ErrorResponse(false);
			errorResponse.setError(buff.toString());
			return errorResponse;
		}
		if (requestParams.get(OPENID_NS) == null) {
			StringBuilder buff = new StringBuilder();
			buff.append("key value '");
			buff.append(OPENID_NS);
			buff.append("' is empty");
			logger.error(buff.toString());
			ErrorResponse errorResponse = new ErrorResponse(false);
			errorResponse.setError(buff.toString());
			return errorResponse;
		} else {
			if (!AbstractMessage.OPENID_NS_20.equals(requestParams
					.get(OPENID_NS))) {
				StringBuilder buff = new StringBuilder();
				buff.append("Unsupported OpenId namespace '");
				buff.append(requestParams.get(OPENID_NS));
				buff.append("'");
				logger.error(buff.toString());
				ErrorResponse errorResponse = new ErrorResponse(false);
				errorResponse.setError(buff.toString());
				return errorResponse;
			}
		}
		return null;
	}

}

package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;

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

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(MODE) == null) {
			StringBuilder buff = new StringBuilder();
			buff.append("key value '");
			buff.append(MODE);
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
			if (!OPENID_NS_20.equals(requestParams.get(OPENID_NS))) {
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

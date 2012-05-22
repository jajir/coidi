package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;

/**
 * When no previous dispatcher process message then this report that message is
 * invalid.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherTerminator implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		ErrorResponse errorResponse = new ErrorResponse(false);
		StringBuilder buff = new StringBuilder();
		buff.append("Unable to process incoming message, incorrect openid.mode '");
		buff.append(requestParams.get(OPENID_MODE));
		buff.append("'\n");
		for (Entry<String, String> entry : requestParams.entrySet()) {
			buff.append(entry.getKey());
			buff.append("=");
			buff.append(entry.getValue());
			buff.append("\n");
		}
		logger.warn(buff.toString());
		errorResponse
				.setError("Unable to process incoming message, incorrect openid.mode '"
						+ requestParams.get(OPENID_MODE) + "'\n");
		return errorResponse;
	}

}

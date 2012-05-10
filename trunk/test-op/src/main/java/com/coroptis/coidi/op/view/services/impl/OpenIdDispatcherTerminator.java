package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;
import java.util.Map.Entry;

import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.services.OpenIdResponseText;

/**
 * When no previous dispatcher process message then this report that message is
 * invalid.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherTerminator implements OpenIdDispatcher {

	@Override
	public OpenIdResponseText process(Map<String, String> requestParams) {
		StringBuilder buff = new StringBuilder();
		buff.append("Unable to process incoming message, incorrect openid.mode '");
		buff.append(requestParams.get(MODE));
		buff.append("'\n");
		for (Entry<String, String> entry : requestParams.entrySet()) {
			buff.append(entry.getKey());
			buff.append("=");
			buff.append(entry.getValue());
			buff.append("\n");
		}
		return new OpenIdResponseText(buff.toString());
	}

}

package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.view.services.XrdsService;
import com.coroptis.coidi.op.view.utils.XrdsStreamResponse;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
public class User { // NO_UCD

	@Property
	private String userName;

	@Inject
	private Request request;

	@Inject
	private Response response;

	@Inject
	private XrdsService xrdsService;

	StreamResponse onActivate(final String userName) {
		String accept = request.getHeader("Accept");
		if ("HEAD".equals(request.getMethod())) {
			response.setHeader("X-XRDS-Location",
					xrdsService.getXrdsLocation(userName));
		}
		if (accept != null && accept.contains("application/xrds+xml")) {
			// response stream response
			return new XrdsStreamResponse(xrdsService.getDocument(userName));
		}
		this.userName = userName;
		if (userName == null) {
			new CoidiException("user not found");
		}
		return null;
	}

	String onPassivate() {
		return userName;
	}

}

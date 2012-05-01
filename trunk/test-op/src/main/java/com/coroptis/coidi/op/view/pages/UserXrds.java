package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.view.services.XrdsService;
import com.coroptis.coidi.op.view.utils.XrdsStreamResponse;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
public class UserXrds {

	@Inject
	private XrdsService xrdsService;

	private String userName;

	StreamResponse onActivate(final String userName) {
		this.userName = userName;
		if (userName == null) {
			new CoidiException("user not found");
		}
		return new XrdsStreamResponse(xrdsService.getDocument(userName));
	}

	String onPassivate() {
		return userName;
	}

}

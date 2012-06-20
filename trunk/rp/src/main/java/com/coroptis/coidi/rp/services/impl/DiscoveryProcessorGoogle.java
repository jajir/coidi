package com.coroptis.coidi.rp.services.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.google.common.base.Preconditions;

/**
 * This discovery processor allows user to use their imail for login.
 * 
 * @author jan
 * 
 */
public class DiscoveryProcessorGoogle implements DiscoveryProcessor {

	private final static Logger logger = Logger
			.getLogger(DiscoveryProcessorGoogle.class);

	@Inject
	private DiscoverySupport discoverySupport;

	private Boolean isItEmail(String email) {
		return (email.endsWith(".com"));
	}

	public DiscoveryResult dicovery(String userSuppliedId) {
		Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
		userSuppliedId = userSuppliedId.trim();
		if (isItEmail(userSuppliedId)) {
			logger.debug("It's gmail id '" + userSuppliedId + "'");
			return discoverySupport
					.getXrdsDocument("https://www.google.com/accounts/o8/id");
		} else {
			return null;
		}
	}

}

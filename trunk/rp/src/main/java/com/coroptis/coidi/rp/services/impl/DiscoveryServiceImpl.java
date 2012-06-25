package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoveryService;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.google.common.base.Preconditions;

public class DiscoveryServiceImpl implements DiscoveryService {

	@Inject
	private DiscoveryProcessor discoveryProcessor;

	@Inject
	private DiscoverySupport discoverySupport;

	@Override
	public DiscoveryResult dicovery(final String userSuppliedId) {
		Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
		String claimedId = userSuppliedId.trim();
		if (!discoverySupport.isItEmail(claimedId)) {
			claimedId = discoverySupport.normalize(claimedId);
		}
		return discoveryProcessor.dicovery(claimedId);
	}

}

package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoveryService;
import com.google.common.base.Preconditions;

public class DiscoveryServiceImpl implements DiscoveryService {

	@Inject
	private DiscoveryProcessor discoveryProcessor;

	@Override
	public DiscoveryResult dicovery(String userSuppliedId) {
		Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
		userSuppliedId = userSuppliedId.trim();
		return discoveryProcessor.dicovery(userSuppliedId);
	}

}

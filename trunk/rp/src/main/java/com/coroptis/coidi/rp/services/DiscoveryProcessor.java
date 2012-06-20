package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * Chain of command definition.
 * 
 * @author jan
 * 
 */
public interface DiscoveryProcessor {

	DiscoveryResult dicovery(String userSuppliedId);

}

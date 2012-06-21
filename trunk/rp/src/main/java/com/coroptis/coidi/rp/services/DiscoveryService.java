package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * Entry point for discovery process.
 * 
 * @author jan
 * 
 */
public interface DiscoveryService {

	/**
	 * Performs discovery on given user supplied ID.
	 * 
	 * @param userSuppliedId
	 *            required user supplied id
	 * @return discovery result
	 */
	DiscoveryResult dicovery(String userSuppliedId);

}

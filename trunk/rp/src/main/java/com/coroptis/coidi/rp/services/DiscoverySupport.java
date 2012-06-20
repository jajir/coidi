package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * Provide support for particular {@link DiscoveryProcessor} implementations.
 * 
 * @author jan
 * 
 */
public interface DiscoverySupport {

	/**
	 * Try to get and process XRDS document from giver URL.
	 * 
	 * @param xrdsDocumentUrl
	 *            required XRDS document URL
	 * @return resolved XRDS document
	 * @throws AuthenticationProcessException
	 *             When exception occurs during obtaining discovery result than
	 *             this exception with proper description is throws
	 */
	public DiscoveryResult getXrdsDocument(String xrdsDocumentUrl)
			throws AuthenticationProcessException;

}

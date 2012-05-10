package com.coroptis.coidi.op.view.services.impl;

import java.util.Set;

import org.apache.log4j.Logger;

import com.coroptis.coidi.op.view.base.DiscoveryResult;
import com.coroptis.coidi.op.view.services.DiscoveryProcessor;

/**
 * Yadis.
 * 
 * @author jan
 * 
 */
public class DiscoveryProcessorYadis implements DiscoveryProcessor {

	private final static Logger logger = Logger
			.getLogger(DiscoveryProcessorYadis.class);

	public DiscoveryResult Dicovery(String userSuppliedId) {
		return null;
	}

	public void discover(String url, int maxRedirects, Set serviceTypes) {
		logger.info("yadis resolving ... at '" + url + "'");
	}

}

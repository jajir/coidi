package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;

public class AuthReqPreconditions implements AuthReq {

	@Inject
	private Logger logger;

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		if (discoveryResult.getPreferedService() == null) {
			logger.info("Discovery process failed, found XRDS document is not valid.");
			throw new AuthenticationProcessException(
					"Discovery process failed, found XRDS document is not valid.");
		}
		/**
		 * Look for OP identifier element
		 */
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_OPENID_2_0)) {
			return false;
		} else {
			/**
			 * Look for Claimed identifier element
			 */
			if (discoveryResult.getPreferedService().idPresent(
					XrdService.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
				return false;
			} else {
				logger.info("Discovery process failed, found XRDS document doens't contains"
						+ " neither OP identifier element nor Claimend identifier element.");
				throw new AuthenticationProcessException(
						"Discovery process failed, found XRDS document is not valid.");
			}
		}
	}
}

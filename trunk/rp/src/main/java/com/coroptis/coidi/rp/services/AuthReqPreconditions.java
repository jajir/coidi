package com.coroptis.coidi.rp.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;

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
		if (!discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_OPENID_2_0)) {
			logger.info("Discovery process failed, found XRDS document doens't contains OpenID name space.");
			throw new AuthenticationProcessException(
					"Discovery process failed, found XRDS document doens't contains OpenID name space.");
		}
		return false;
	}
}

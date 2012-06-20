package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.RpService;

public class RpServiceImpl implements RpService {

	@Inject
	private Logger logger;

	@Inject
	private AuthReq authReq;

	@Inject
	@Symbol("openid.realm")
	private String realm;

	@Override
	public String authentication(final DiscoveryResult discoveryResult,
			final SessionType sessionType, final String mode,
			final String userSuppliedId, final Association association,
			final String returnTo) {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		if (association == null) {
			// no association handle --> stateless mode
		} else {
			authenticationRequest.setAssocHandle(association.getAssocHandle());
		}
		authenticationRequest.setIdentity(userSuppliedId);
		authenticationRequest.setClaimedId(userSuppliedId);
		authenticationRequest.setMode(mode);
		authenticationRequest.setRealm(realm);
		authenticationRequest.setReturnTo(returnTo);
		authReq.applyExtension(authenticationRequest, discoveryResult);
		logger.debug("authentication: " + authenticationRequest.getMessage());
		return authenticationRequest.getMessage();
	}

}

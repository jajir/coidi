package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationParameters;
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
			final Association association,
			final AuthenticationParameters authenticationParameters) {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		if (association == null) {
			// no association handle --> stateless mode
		} else {
			authenticationRequest.setAssocHandle(association.getAssocHandle());
		}

		// TODO refactor it
		authenticationRequest.setIdentity(discoveryResult.getClaimedId());
		authenticationRequest.setClaimedId(discoveryResult.getClaimedId());
		authenticationRequest.setMode(authenticationParameters.getMode());
		authenticationRequest.setRealm(realm);
		authenticationRequest.setReturnTo(authenticationParameters
				.getReturnTo());
		authReq.process(authenticationRequest, discoveryResult,
				authenticationParameters.getParameters());
		logger.debug("authentication: " + authenticationRequest.getMessage());
		return authenticationRequest.getMessage();
	}

}

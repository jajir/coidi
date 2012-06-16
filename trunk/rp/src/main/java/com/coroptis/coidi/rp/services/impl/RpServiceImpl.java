package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
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
	
	@Override
	public String authentication(DiscoveryResult discoveryResult,
			SessionType sessionType, String mode, String userSuppliedId,
			Association association) {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		if (association == null) {
			// no association handle --> stateless mode
		} else {
			authenticationRequest.setAssocHandle(association.getAssocHandle());
		}
		authenticationRequest.setIdentity(userSuppliedId);
		authenticationRequest.setMode(mode);
		authReq.applyExtension(authenticationRequest, discoveryResult);
		logger.debug("authentication: " + authenticationRequest.getMessage());
		return authenticationRequest.getMessage();
	}

}

package com.coroptis.coidi.rp.view.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.view.services.RpService;

public class RpServiceImpl implements RpService {

	@Inject
	private Logger logger;

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
		authenticationRequest.setRealm("not in use");
		authenticationRequest.setReturnTo("http://localhost:8081/");
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());
		logger.debug("authentication: " + authenticationRequest.getMessage());
		return authenticationRequest.getMessage();
	}

}

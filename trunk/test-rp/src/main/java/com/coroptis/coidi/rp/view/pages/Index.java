package com.coroptis.coidi.rp.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.view.services.AssociationServise;
import com.coroptis.coidi.rp.view.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryResult;

public class Index {

	@Property
	private String userSuppliedId;

	@Inject
	private DiscoveryProcessor discoveryProcessor;

	@Inject
	private AssociationServise associationServise;

	@Inject
	private Logger logger;

	@SessionState
	private Association association;

	URL onSuccess() throws MalformedURLException {
		DiscoveryResult discoveryResult = discoveryProcessor
				.dicovery(userSuppliedId);
		association = associationServise.generateAssociation(discoveryResult
				.getEndPoint());

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setAssocHandle(association.getAssocHandle());
		authenticationRequest.setIdentity(userSuppliedId);
		authenticationRequest.setMode("checkid_immediate");
		authenticationRequest.setRealm("not in use");
		authenticationRequest.setReturnTo("http://localhost:8081/");
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());

		URL redirectTo = new URL(authenticationRequest.getMessage());
		logger.debug("url: " + redirectTo);
		return redirectTo;
	}
}

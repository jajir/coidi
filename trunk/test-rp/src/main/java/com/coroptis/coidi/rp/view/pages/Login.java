package com.coroptis.coidi.rp.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.view.services.AssociationServise;
import com.coroptis.coidi.rp.view.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryResult;
import com.coroptis.coidi.rp.view.util.AccessOnlyForUnsigned;

@AccessOnlyForUnsigned
public class Login {

	@Inject
	private Logger logger;

	@Inject
	private DiscoveryProcessor discoveryProcessor;

	@Inject
	private AssociationServise associationServise;

	@SessionState
	private Association association;

	@Property
	private String userSuppliedId;

	@Property
	@Validate("required")
	private AssociationType associationType;

	@Property
	@Validate("required")
	private SessionType sessionType;

	@Property
	private boolean statelessMode;

	public void onActivate() {
		associationType = AssociationType.HMAC_SHA1;
		sessionType = SessionType.DH_SHA1;
	}

	URL onSuccess() throws MalformedURLException {
		logger.debug("association type: " + associationType);
		logger.debug("session type    : " + sessionType);

		DiscoveryResult discoveryResult = discoveryProcessor
				.dicovery(userSuppliedId);

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		if (statelessMode) {
			// no association handle
		} else {
			association = associationServise
					.generateAssociation(discoveryResult.getEndPoint(),
							sessionType, associationType);
			authenticationRequest.setAssocHandle(association.getAssocHandle());
		}
		authenticationRequest.setIdentity(userSuppliedId);
		authenticationRequest.setMode("checkid_setup");
		// authenticationRequest.setMode("checkid_immediate");
		authenticationRequest.setRealm("not in use");
		authenticationRequest.setReturnTo("http://localhost:8081/");
		authenticationRequest.put("go_to", discoveryResult.getEndPoint());
		URL redirectTo = new URL(authenticationRequest.getMessage());
		logger.debug("url: " + redirectTo);
		return redirectTo;
	}

	public Boolean getShowOptions() {
		return !statelessMode;
	}
}

package com.coroptis.coidi.rp.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AssociationServise;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.RpService;
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

	@Property
	private String mode;

	@Inject
	private RpService rpService;

	public void onActivate() {
		associationType = AssociationType.HMAC_SHA1;
		sessionType = SessionType.DH_SHA1;
	}

	URL onSuccess() throws MalformedURLException {
		logger.debug("association type: " + associationType);
		logger.debug("session type    : " + sessionType);
		logger.debug("mode            : " + mode);

		DiscoveryResult discoveryResult = discoveryProcessor
				.dicovery(userSuppliedId);

		if (statelessMode) {
			return new URL(rpService.authentication(discoveryResult,
					sessionType, mode, userSuppliedId, null));
		} else {
			association = associationServise
					.generateAssociation(discoveryResult.getEndPoint(),
							sessionType, associationType);
			return new URL(rpService.authentication(discoveryResult,
					sessionType, mode, userSuppliedId, association));
		}
	}

	public Boolean getShowOptions() {
		return !statelessMode;
	}
}

package com.coroptis.coidi.rp.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.base.AuthenticationParameters;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AssociationServise;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;
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

	@Inject
	@Symbol("common.return_to")
	private String return_to;

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

	@Component
	private Form openId;

	private String authenticationRequestUrl;

	public void onActivate() {
		associationType = AssociationType.HMAC_SHA1;
		sessionType = SessionType.DH_SHA1;
		mode = "checkid_setup";
	}

	void onValidateFromOpenId() {
		logger.debug("association type: " + associationType);
		logger.debug("session type    : " + sessionType);
		logger.debug("mode            : " + mode);
		logger.debug("userSuppliedId  : " + userSuppliedId);

		try {
			DiscoveryResult discoveryResult = discoveryProcessor
					.dicovery(userSuppliedId);
			AuthenticationParameters params = new AuthenticationParameters();
			params.setMode(mode);
			params.setReturnTo(return_to);
			params.setSessionType(sessionType);
			params.setUserSuppliedId(userSuppliedId);

			if (statelessMode) {
				authenticationRequestUrl = rpService.authentication(
						discoveryResult, null, params);
			} else {
				association = associationServise.generateAssociation(
						discoveryResult.getEndPoint(), sessionType,
						associationType);
				authenticationRequestUrl = rpService.authentication(
						discoveryResult, association, params);
			}
			logger.debug("authenticationRequestUrl: "
					+ authenticationRequestUrl);
		} catch (AuthenticationProcessException e) {
			openId.recordError(e.getMessage());
		}
	}

	URL onSuccess() throws MalformedURLException {
		return new URL(authenticationRequestUrl);
	}

	public Boolean getShowOptions() {
		return !statelessMode;
	}
}

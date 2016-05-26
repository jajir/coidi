/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.rp.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.base.AuthenticationParameters;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.iocsupport.RpBinding;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;
import com.coroptis.coidi.rp.view.util.AccessOnlyForUnsigned;
import com.google.common.base.Joiner;

@AccessOnlyForUnsigned
public class Login {

	enum Gendre {
		M, F
	}

	@Inject
	private Logger logger;

	@Inject
	private RpBinding rpBinding;

	@Inject
	@Symbol("common.return_to")
	private String return_to;

	@Inject
	private Messages messages;

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

	@Component
	private Form openId;

	private String authenticationRequestUrl;

	@Property
	private boolean simpleRegistrationExtension;

	private boolean sreg[][] = new boolean[2][9];

	@Property
	private Integer index;

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
			DiscoveryResult discoveryResult = rpBinding.getDiscoveryProcessor().dicovery(userSuppliedId);
			if (discoveryResult == null) {
				openId.recordError("Invalid user supplied ID, unable to get XRDS document.");
				return;
			}
			logger.debug("claimed ID from discovery '" + discoveryResult.getClaimedId() + "'");
			AuthenticationParameters params = new AuthenticationParameters();
			params.setMode(mode);
			params.setReturnTo(return_to);
			params.setSessionType(sessionType);
			params.setUserSuppliedId(userSuppliedId);

			/**
			 * Simple registration extension 1.1
			 */
			if (simpleRegistrationExtension) {
				params.getParameters().put("sreg.ns", OpenIdNs.TYPE_SREG_1_1);
				Joiner sregReq = Joiner.on(",").skipNulls();
				String req = sregReq.join(sreg[0][0] ? "nickname" : null, sreg[0][1] ? "email" : null,
						sreg[0][2] ? "fullname" : null, sreg[0][3] ? "dob" : null, sreg[0][4] ? "gendre" : null,
						sreg[0][5] ? "postcode" : null, sreg[0][6] ? "country" : null, sreg[0][7] ? "language" : null,
						sreg[0][8] ? "timezone" : null);
				params.getParameters().put("sreg.required", req);
				Joiner sregOpt = Joiner.on(",").skipNulls();
				String opt = sregOpt.join(sreg[1][0] ? "nickname" : null, sreg[1][1] ? "email" : null,
						sreg[1][2] ? "fullname" : null, sreg[1][3] ? "dob" : null, sreg[1][4] ? "gendre" : null,
						sreg[1][5] ? "postcode" : null, sreg[1][6] ? "country" : null, sreg[1][7] ? "language" : null,
						sreg[1][8] ? "timezone" : null);
				params.getParameters().put("sreg.optional", opt);
				logger.debug("sregistration required: " + req);
				logger.debug("sregistration optional: " + opt);
			}

			if (statelessMode) {
				authenticationRequestUrl = rpBinding.getRpService().authentication(discoveryResult, null, params);
			} else {
				association = rpBinding.getAssociationFactory().generateAssociation(discoveryResult.getEndPoint(),
						sessionType, associationType);
				authenticationRequestUrl = rpBinding.getRpService().authentication(discoveryResult, association, params);
			}
			logger.debug("authenticationRequestUrl: " + authenticationRequestUrl);
		} catch (AuthenticationProcessException e) {
			openId.recordError(e.getMessage());
		}
	}

	public Boolean getSregReq() {
		return sreg[0][index];
	}

	public void setSregReq(Boolean b) {
		sreg[0][index] = b;
	}

	public Boolean getSregOpt() {
		return sreg[1][index];
	}

	public void setSregOpt(Boolean b) {
		sreg[1][index] = b;
	}

	public String getSregMsg() {
		return messages.get("sreg" + index);
	}

	URL onSuccess() throws MalformedURLException {
		return new URL(authenticationRequestUrl);
	}

	public Boolean getShowOptions() {
		return !statelessMode;
	}
}

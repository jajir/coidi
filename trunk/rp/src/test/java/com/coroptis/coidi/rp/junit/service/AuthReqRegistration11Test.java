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
package com.coroptis.coidi.rp.junit.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.classextension.EasyMock;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.impl.AuthReqRegistration10;
import com.coroptis.coidi.rp.services.impl.AuthReqRegistration11;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;

public class AuthReqRegistration11Test extends AbstractLocalJunitTest {

	private final static String SERVICE_NAME = "realService";

	private AuthReq authReq;

	private AuthenticationRequest authenticationRequest;

	private Map<String, String> params;

	private XrdService xrdService;

	public void testProcess_missingSupport() throws Exception {
		EasyMock.expect(services.getDiscoveryResult().getPreferedService())
				.andReturn(xrdService);

		services.replay();
		authReq.process(authenticationRequest, services.getDiscoveryResult(),
				params);

		services.verify();
	}

	public void testProcess_invalid_regNewIdentityParam() throws Exception {
		xrdService.getTypes().add(XrdService.TYPE_SREG_1_1);
		EasyMock.expect(services.getDiscoveryResult().getPreferedService())
				.andReturn(xrdService);

		services.replay();
		authReq.process(authenticationRequest, services.getDiscoveryResult(),
				params);

		services.verify();
	}

	public void testProcess_invalid_nameSpace() throws Exception {
		params.put(AuthReqRegistration10.REG_NEW_IDENTITY, "true");
		EasyMock.expect(services.getDiscoveryResult().getPreferedService())
				.andReturn(xrdService);

		services.replay();
		authReq.process(authenticationRequest, services.getDiscoveryResult(),
				params);

		services.verify();
	}

	public void testProcess() throws Exception {
		params.put(AuthReqRegistration10.REG_NEW_IDENTITY, "true");
		xrdService.getTypes().add(XrdService.TYPE_SREG_1_1);
		EasyMock.expect(services.getDiscoveryResult().getPreferedService())
				.andReturn(xrdService);

		services.replay();
		authReq.process(authenticationRequest, services.getDiscoveryResult(),
				params);

		services.verify();
		assertEquals(XrdService.TYPE_SREG_1_1,
				authenticationRequest.get("openid.ns.sreg"));
		assertEquals("name", authenticationRequest.get("openid.sreg.required"));
		assertEquals("email", authenticationRequest.get("openid.sreg.optional"));
		assertEquals("http://www.example.com/policy-description",
				authenticationRequest.get("openid.sreg.policy_url"));
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(AuthReq.class, AuthReqRegistration11.class).withId(
				SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		System.setProperty("common.extension.registration.requiredFields",
				"name");
		System.setProperty("common.extension.registration.optionalFields",
				"email");
		System.setProperty("common.extension.registration.policyUrl",
				"http://www.example.com/policy-description");
		super.setUp();
		authReq = getService(SERVICE_NAME, AuthReq.class);
		authenticationRequest = new AuthenticationRequest();
		params = new HashMap<String, String>();
		xrdService = new XrdService();
	}

	@Override
	protected void tearDown() throws Exception {
		authReq = null;
		authenticationRequest = null;
		params = null;
		xrdService = null;
		super.tearDown();
	}
}

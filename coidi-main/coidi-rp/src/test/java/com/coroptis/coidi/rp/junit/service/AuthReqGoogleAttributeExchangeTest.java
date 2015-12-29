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
import com.coroptis.coidi.rp.services.impl.AuthReqGoogleAttributeExchange;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;

public class AuthReqGoogleAttributeExchangeTest extends AbstractLocalJunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthReq authReq;

    private AuthenticationRequest authenticationRequest;

    private Map<String, String> params;

    private XrdService xrdService;

    public void testProcess_missingSupport() throws Exception {
	EasyMock.expect(services.getDiscoveryResult().getPreferedService()).andReturn(xrdService);

	services.replay();
	authReq.process(authenticationRequest, services.getDiscoveryResult(), params);

	assertNull(authenticationRequest.get("openid.ns.ax"));
	services.verify();
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthReq.class, AuthReqGoogleAttributeExchange.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	System.setProperty("common.extension.registration.requiredFields", "name");
	System.setProperty("common.extension.registration.optionalFields", "email");
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

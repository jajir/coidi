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
package com.coroptis.coidi.op.junit.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.util.BaseJunitTest;

public class AuthenticationServiceTest extends BaseJunitTest {

	private final static String SERVICE_NAME = "realService";

	private AuthenticationService service;

	public void testIsAuthenticationRequest() throws Exception {
		AuthenticationRequest request = new AuthenticationRequest();
		request.setMode(AuthenticationRequest.MODE_CHECKID_SETUP);
		request.setIdentity("http://www.oid.com/user/karel");
		assertTrue(service.isAuthenticationRequest(request));
	}

	public void testIsAuthenticationRequest_noIdent() throws Exception {
		AuthenticationRequest request = new AuthenticationRequest();
		request.setMode(AuthenticationRequest.MODE_CHECKID_SETUP);
		assertFalse(service.isAuthenticationRequest(request));
	}

	public void testGetNameSpace() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid.ns.ax", OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);
		AuthenticationRequest request = new AuthenticationRequest(map);
		String ret = service.getNameSpace(request,
				OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);

		assertEquals("ax", ret);
	}

	public void testGetNameSpace_noSuchNameSpace() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		AuthenticationRequest request = new AuthenticationRequest(map);
		String ret = service.getNameSpace(request,
				OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);

		assertNull(ret);
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(AuthenticationService.class,
				AuthenticationServiceImpl.class).withId(SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = getService(SERVICE_NAME, AuthenticationService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		service = null;
		super.tearDown();
	}
}

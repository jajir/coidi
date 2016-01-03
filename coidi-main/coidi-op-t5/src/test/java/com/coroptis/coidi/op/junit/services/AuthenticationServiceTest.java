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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AuthenticationServiceTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationService service;

    public void testGetNameSpace() throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("openid.ns.ax", OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);
	AuthenticationRequest request = new AuthenticationRequest(map);
	String ret = service.getNameSpace(request, OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);

	assertEquals("ax", ret);
    }

    public void testGetNameSpace_noSuchNameSpace() throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	AuthenticationRequest request = new AuthenticationRequest(map);
	String ret = service.getNameSpace(request, OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);

	assertNull(ret);
    }

    public void test_convertHttpRequestParametersToMap() throws Exception {
	HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
	EasyMock.expect(request.getParameterNames()).andReturn(
		Collections.enumeration(Sets.newHashSet("openid.ns", "openid.identity")));
	EasyMock.expect(request.getParameter("openid.ns"))
		.andReturn("http://openid.net/signon/1.0").times(2);
	EasyMock.expect(request.getParameter("openid.identity")).andReturn("kachna").times(2);
	EasyMock.replay(request);
	Map<String, String> ret = service.convertHttpRequestParametersToMap(request);

	assertNotNull(ret);
	EasyMock.verify(request);
    }

    public void test_convertHttpRequestParametersToMap_duplicatedKeys() throws Exception {
	HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
	EasyMock.expect(request.getParameterNames()).andReturn(
		Collections.enumeration(Lists.newArrayList("openid.ns", "openid.ns",
			"openid.identity")));
	EasyMock.expect(request.getParameter("openid.ns"))
		.andReturn("http://openid.net/signon/1.0").times(4);
	EasyMock.replay(request);
	try {
	    service.convertHttpRequestParametersToMap(request);
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}

	EasyMock.verify(request);
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationService.class, AuthenticationServiceImpl.class).withId(
		SERVICE_NAME);
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

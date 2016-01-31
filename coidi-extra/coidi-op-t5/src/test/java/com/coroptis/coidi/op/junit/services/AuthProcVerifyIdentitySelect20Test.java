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
import java.util.HashSet;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentitySelect20;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AuthProcVerifyIdentitySelect20Test extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcVerifyIdentitySelect20.class).withId(
		SERVICE_NAME);
    }

    public void testProcess_identitySelect_identityWasSelectedByUser() throws Exception {
	request.setIdentity("http://specs.openid.net/auth/2.0/identifier_select");
	request.setSelectedIdentity("http://www.coidi.com/identity/qwe");
	services.replay();

	AbstractMessage ret = service.process(request, response, null, new HashSet<String>());

	assertNull(ret);
	assertEquals("http://www.coidi.com/identity/qwe", response.getIdentity());
	assertEquals("http://www.coidi.com/identity/qwe", response.getClaimedId());
	assertEquals("http://www.coidi.com/identity/qwe", request.getIdentity());
	assertEquals("http://www.coidi.com/identity/qwe", request.getClaimedId());
	services.verify();
    }

    public void testProcess_identitySelect_identityWasNotSelectedByUser() throws Exception {
	request.setIdentity("http://specs.openid.net/auth/2.0/identifier_select");
	request.setSelectedIdentity(null);
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().applicationError(
			(String) EasyMock.anyObject(), EasyMock.eq("selectIdentity"))).andReturn(
		err);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, new HashSet<String>());

	assertSame(err, ret);
	services.verify();
    }

    public void testProcess_standardIdentity() throws Exception {
	services.replay();

	AbstractMessage ret = service.process(request, response, null, new HashSet<String>());

	assertNull(ret);
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);
	Map<String, String> params = new HashMap<String, String>();
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("penid.claimed_id", "http://www.coidi.com/identity/qwe");

	request = new AuthenticationRequest(params);
	response = new AuthenticationResponse();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	response = null;
	super.tearDown();
    }
}

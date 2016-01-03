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
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity11;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.TestUserSession;
import com.coroptis.coidi.op.util.UserMock;

public class AuthProcVerifyIdentity11Test extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;
    private TestUserSession session;
    private UserMock user;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcVerifyIdentity11.class).withId(
		SERVICE_NAME);
    }

    public void testProcess() throws Exception {
	session.setUser(user);
	EasyMock.expect(
		services.getOpenIdRequestTool()
			.verify("http://www.coidi.com/identity/qwe", session)).andReturn(true);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNull(ret);
	services.verify();
    }

    public void testProcess_userIsNotLogged() throws Exception {
	session.setUser(null);
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError("User is not logged at OP",
			"http://openid.net/signon/1.1")).andReturn(err);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNotNull(ret);
	assertSame(err, ret);
	services.verify();
    }

    public void testProcess_userIsNotValid() throws Exception {
	session.setUser(user);
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getOpenIdRequestTool()
			.verify("http://www.coidi.com/identity/qwe", session)).andReturn(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"Requested identity 'http://www.coidi.com/identity/qwe' doesn't exists.",
			"http://openid.net/signon/1.1")).andReturn(err);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNotNull(ret);
	assertSame(err, ret);
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);

	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_SETUP);
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.claimed_id", "http://www.coidi.com/identity/qwe");
	request = new AuthenticationRequest(params);
	response = new AuthenticationResponse();
	session = new TestUserSession();
	user = new UserMock();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	response = null;
	user = null;
	session = null;
	super.tearDown();
    }
}

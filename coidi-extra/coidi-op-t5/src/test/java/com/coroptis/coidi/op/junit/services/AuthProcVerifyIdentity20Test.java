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

import javax.servlet.http.HttpSession;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity20;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.MockHttpSession;

public class AuthProcVerifyIdentity20Test extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;
    private HttpSession session;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcVerifyIdentity20.class)
		.withId(SERVICE_NAME);
    }

    public void testProcess() throws Exception {
	EasyMock.expect(services.getUserVerifier().isUserLogged(session)).andReturn(true);
	EasyMock.expect(
		services.getUserVerifier().verify("http://www.coidi.com/identity/qwe", session))
		.andReturn(true);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNull(ret);
	services.verify();
    }

    public void testProcess_missingIdentity() throws Exception {
	EasyMock.expect(services.getUserVerifier().isUserLogged(session)).andReturn(true);
	request.setIdentity(null);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNull(ret);
	services.verify();
    }

    public void testProcess_userIsNotLogged() throws Exception {
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(services.getUserVerifier().isUserLogged(session)).andReturn(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().buildError("User is not logged at OP"))
		.andReturn(err);
	services.replay();
	AbstractMessage ret = service.process(request, response, session, null);

	assertNotNull(ret);
	assertSame(err, ret);
	services.verify();
    }

    public void testProcess_userIsNotValid() throws Exception {
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(services.getUserVerifier().isUserLogged(session)).andReturn(true);
	EasyMock.expect(
		services.getUserVerifier().verify("http://www.coidi.com/identity/qwe", session))
		.andReturn(false);
	EasyMock.expect(services.getNegativeResponseGenerator().buildError("Requested identity '",
		"http://www.coidi.com/identity/qwe", "' doesn't exists.")).andReturn(err);
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
	session = new MockHttpSession();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	response = null;
	session = null;
	super.tearDown();
    }
}

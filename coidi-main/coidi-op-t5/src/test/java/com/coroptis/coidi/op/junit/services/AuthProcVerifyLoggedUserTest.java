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

import java.util.HashSet;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.TestUserSession;
import com.coroptis.coidi.op.util.UserMock;

public class AuthProcVerifyLoggedUserTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private UserMock user;
    private TestUserSession session;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcVerifyLoggedUser.class).withId(
		SERVICE_NAME);
    }

    public void testProcess_userIsLoggedIn() throws Exception {
	session.setUser(user);
	services.replay();

	AbstractMessage ret = service.process(request, null, session, new HashSet<String>());

	assertNull(ret);
	services.verify();
    }

    public void testProcess_userIsNotLoggedIn() throws Exception {
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().applicationError("User is not logged in",
			"pleaseLogin")).andReturn(err);
	services.replay();
	AbstractMessage ret = service.process(request, null, session, new HashSet<String>());

	assertSame(err, ret);
	assertSame(request, session.getAuthenticationRequest());
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);
	request = new AuthenticationRequest();
	user = new UserMock();
	session = new TestUserSession();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	user = null;
	session = null;
	super.tearDown();
    }
}

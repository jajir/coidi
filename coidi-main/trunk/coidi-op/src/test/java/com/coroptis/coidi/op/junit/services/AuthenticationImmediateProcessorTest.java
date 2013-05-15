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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.op.services.AuthenticationImmediateProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthenticationImmediateProcessorImpl;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate20;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.IdentityMock;
import com.coroptis.coidi.op.util.TestUserSession;

/**
 * Test for {@link OpenidDispatcherAuthenticationImmediate20}.
 * 
 * @author jirout
 * 
 */
public class AuthenticationImmediateProcessorTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationImmediateProcessor service;

    private AssociationBean association, associationInvalid;

    private IdentityMock identity;

    private TestUserSession userSession;

    private AbstractMessage positiveResponse;

    private ErrorResponse errorResponse;

    public void testProcess_invalid_authentication_request() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"authentication request doesn't contains any idenity field")).andReturn(
		errorResponse);
	services.replay();
	AbstractMessage ret = service.process(authenticationRequest, userSession);

	assertNotNull(ret);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    public void testProcess_invalid_identity() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"There is no such identity 'http://pond.com/duck'")).andReturn(
		errorResponse);
	EasyMock.expect(
		services.getIdentityService().getByOpLocalIdentifier("http://pond.com/duck"))
		.andReturn(null);
	services.replay();
	AbstractMessage ret = service.process(authenticationRequest, userSession);

	assertNotNull(ret);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    public void testProcess_identity_is_not_logged() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"Idenity 'http://pond.com/duck' is not logged in"))
		.andReturn(errorResponse);
	EasyMock.expect(
		services.getIdentityService().getByOpLocalIdentifier("http://pond.com/duck"))
		.andReturn(identity);
	EasyMock.expect(services.getIdentityService().isIdentityLogged(userSession, identity))
		.andReturn(false);
	services.replay();
	AbstractMessage ret = service.process(authenticationRequest, userSession);

	assertNotNull(ret);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    @SuppressWarnings("unchecked")
    public void testProcess_success() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(
		services.getIdentityService().getByOpLocalIdentifier("http://pond.com/duck"))
		.andReturn(identity);
	EasyMock.expect(services.getIdentityService().isIdentityLogged(userSession, identity))
		.andReturn(true);
	EasyMock.expect(
		services.getAuthenticationProcessor().process(
			(AuthenticationRequest) EasyMock.anyObject(),
			(AuthenticationResponse) EasyMock.anyObject(), EasyMock.eq(identity),
			(Set<String>) EasyMock.anyObject())).andReturn(positiveResponse);
	services.replay();
	AbstractMessage ret = service.process(authenticationRequest, userSession);

	assertNotNull(ret);
	assertTrue(ret instanceof AuthenticationResponse);
	services.verify();
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationImmediateProcessor.class, AuthenticationImmediateProcessorImpl.class).withId(
		SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationImmediateProcessor.class);
	association = new AssociationBean();
	association.setAssocHandle("h342usd09d");
	// next assoc handle should be valid for 1 hour.
	association.setExpiredIn(new Date(new Date().getTime() + 1000 * 60 * 60));

	associationInvalid = new AssociationBean();
	associationInvalid.setAssocHandle("324guy4321j");
	// next assoc handle should was invalidated before 1 hour.
	associationInvalid.setExpiredIn(new Date(new Date().getTime() - 1000 * 60 * 60));

	identity = new IdentityMock();
	identity.setEmail("duck@pond.com");

	userSession = new TestUserSession();

	positiveResponse = new AuthenticationResponse();

	errorResponse = new ErrorResponse(false);
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	association = null;
	identity = null;
	userSession = null;
	positiveResponse = null;
	errorResponse = null;
	super.tearDown();
    }
}

package com.coroptis.coidi.op.junit.services;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate;
import com.coroptis.coidi.op.util.BaseJunitTest;
import com.coroptis.coidi.op.util.IdentityMock;
import com.coroptis.coidi.op.util.TestUserSession;

/**
 * Test for {@link OpenidDispatcherAuthenticationImmediate}.
 * 
 * @author jirout
 * 
 */
public class OpenidDispatcherAuthenticationImmediateTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private OpenIdDispatcher service;

    private AssociationBean association, associationInvalid;

    private IdentityMock identity;

    private TestUserSession userSession;

    private AbstractMessage positiveResponse;

    private ErrorResponse errorResponse;

    public void testProcess_success_pass() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("h342usd09d")).andReturn(
		association);
	EasyMock.expect(services.getIdentityService().getIdentityByName("http://pond.com/duck"))
		.andReturn(identity);
	EasyMock.expect(services.getIdentityService().isIdentityLogged(userSession, identity))
		.andReturn(true);
	EasyMock.expect(
		services.getAuthenticationProcessor().process(authenticationRequest,
			new AuthenticationResponse(), identity, new HashSet<String>())).andReturn(
		positiveResponse);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(ret);
	assertEquals(positiveResponse, ret);
	services.verify();
    }

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
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(ret);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    public void testProcess_failed_assoc_not_found() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("h342usd09d"))
		.andReturn(null);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"Unable to find association handle 'h342usd09d'")).andReturn(errorResponse);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(ret);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    public void testProcess_failed_expired_assoc_handle() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "324guy4321j");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("324guy4321j")).andReturn(
		associationInvalid);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError((String)
			EasyMock.anyObject())).andReturn(errorResponse);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

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
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("h342usd09d")).andReturn(
		association);
	EasyMock.expect(services.getIdentityService().getIdentityByName("http://pond.com/duck"))
		.andReturn(null);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"There is no such identity 'http://pond.com/duck'")).andReturn(
		errorResponse);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(errorResponse);
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
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("h342usd09d")).andReturn(
		association);
	EasyMock.expect(services.getIdentityService().getIdentityByName("http://pond.com/duck"))
		.andReturn(identity);
	EasyMock.expect(services.getIdentityService().isIdentityLogged(userSession, identity))
		.andReturn(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"Idenity 'http://pond.com/duck' is not logged in"))
		.andReturn(errorResponse);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(errorResponse);
	assertEquals(errorResponse, ret);
	services.verify();
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(OpenIdDispatcher.class, OpenidDispatcherAuthenticationImmediate.class).withId(
		SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, OpenIdDispatcher.class);
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

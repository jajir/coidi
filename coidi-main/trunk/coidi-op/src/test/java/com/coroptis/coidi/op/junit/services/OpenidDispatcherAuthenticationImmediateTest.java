package com.coroptis.coidi.op.junit.services;

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
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate;
import com.coroptis.coidi.op.util.BaseJunitTest;
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

    public void testProcess_success_pass() throws Exception {
	AssociationBean association = new AssociationBean();
	association.setAssocHandle("h342usd09d");

	Identity identity = new Identity();
	identity.setEmail("duck@pond.com");

	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, AuthenticationRequest.MODE_CHECKID_IMMEDIATE);
	params.put(AuthenticationRequest.ASSOC_HANDLE, "h342usd09d");
	params.put(AuthenticationRequest.IDENTITY, "http://pond.com/duck");
	AuthenticationRequest authenticationRequest = new AuthenticationRequest(params);

	TestUserSession userSession = new TestUserSession();

	AbstractMessage response = new ErrorResponse(false);

	EasyMock.expect(
		services.getAuthenticationService().isAuthenticationRequest(authenticationRequest))
		.andReturn(true);
	EasyMock.expect(services.getAssociationDao().getByAssocHandle("h342usd09d")).andReturn(
		association);
	EasyMock.expect(services.getIdentityService().getIdentityByName("http://pond.com/duck"))
		.andReturn(identity);
	EasyMock.expect(services.getIdentityService().isIdentityLogged(userSession, identity))
		.andReturn(true);
	EasyMock.expect(services.getNonceService().createNonce()).andReturn("=09e238308feqk");
	EasyMock.expect(
		services.getSigningService().sign((AuthenticationResponse) EasyMock.anyObject(),
			EasyMock.eq(association))).andReturn("adsffoushfkrw==");
	EasyMock.expect(
		services.getAuthenticationProcessor().process(
			(AuthenticationRequest) EasyMock.eq(authenticationRequest),
			(AuthenticationResponse) EasyMock.anyObject(), EasyMock.eq(identity),
			(Set<String>) EasyMock.anyObject())).andReturn(response);
	// TODO AuthenticationResponse have to used real object
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	assertNotNull(ret);
	assertEquals(response, ret);
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
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	super.tearDown();
    }
}

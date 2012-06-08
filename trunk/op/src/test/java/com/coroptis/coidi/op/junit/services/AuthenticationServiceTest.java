package com.coroptis.coidi.op.junit.services;

import org.apache.tapestry5.ioc.ServiceBinder;

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

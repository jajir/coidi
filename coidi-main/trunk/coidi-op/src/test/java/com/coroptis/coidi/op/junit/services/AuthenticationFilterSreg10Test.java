package com.coroptis.coidi.op.junit.services;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.impl.AuthenticationProcessorSreg10;
import com.coroptis.coidi.op.util.BaseJunitTest;

public class AuthenticationFilterSreg10Test extends BaseJunitTest {

	private final static String SERVICE_NAME = "realService";

	private AuthenticationProcessor service;

	public void testProcess() throws Exception {
	    //FIXME finish that
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(AuthenticationProcessor.class,
				AuthenticationProcessorSreg10.class).withId(SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = getService(SERVICE_NAME, AuthenticationProcessor.class);
	}

	@Override
	protected void tearDown() throws Exception {
		service = null;
		super.tearDown();
	}
}

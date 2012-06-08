package com.coroptis.coidi.core.util;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.test.AbstractJunitTest;

/**
 * 
 * @author jan
 * 
 */
public abstract class BaseJunitTest extends AbstractJunitTest {

	protected Registry registry;

	protected Services services;

	public BaseJunitTest() {
		super(JunitAppModule.class);
	}

	protected <T> T getService(Class<T> serviceInterface) {
		return registry.getService(serviceInterface);
	}

	@Override
	public void bind(ServiceBinder binder) {

	}

	@Override
	protected void setUp() throws Exception {
		services = Services.getServices();
		services.reset();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		services = null;
		super.tearDown();
	}
}

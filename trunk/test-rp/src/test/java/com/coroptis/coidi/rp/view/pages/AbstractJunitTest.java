package com.coroptis.coidi.rp.view.pages;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

public abstract class AbstractJunitTest extends TestCase implements
		AdditionalBinder {

	protected final static Logger logger = Logger
			.getLogger(AbstractJunitTest.class);

	protected static Registry registry;

	protected static Mocks services;

	public final static String MOCK_DATA_PATH = "src/test/resources/mock-data/";

	static {
		System.setProperty("server.role", "junit");
	}

	public <T> T getService(Class<T> serviceInterface) {
		return registry.getService(serviceInterface);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// initialize T5
		logger.debug("loading tapestry 5 registry with server.role: "
				+ System.getProperty("server.role"));
		AdditionalBinderProvider.setAdditionalBinder(this);
		services = Mocks.getServices();
		services.reset();
		RegistryBuilder builder = new RegistryBuilder();
		builder.add(JunitTestModule.class);
		registry = builder.build();
		registry.performRegistryStartup();
	}

	@Override
	protected void tearDown() throws Exception {
		services = null;
		AdditionalBinderProvider.setAdditionalBinder(null);
		registry.shutdown();
		registry = null;
		super.tearDown();
	}
}

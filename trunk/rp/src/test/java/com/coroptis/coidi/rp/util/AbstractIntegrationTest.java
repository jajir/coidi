package com.coroptis.coidi.rp.util;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.test.PageTester;

import com.coroptis.coidi.core.services.CoreModule;

public abstract class AbstractIntegrationTest extends TestCase {

	protected final static Logger logger = Logger
			.getLogger(AbstractIntegrationTest.class);

	protected final static String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.rp";

	protected final static String T5_APPLICATION_NAME = "Rp";

	protected final static String T5_WEBAPP_BASE = "src/main/webapp";

	protected Mocks services;

	static {
		System.setProperty("server.role", "junit");
		System.setProperty("system.property.configuration.directory",
				"non-existing");
		logger.debug("loading T5 registry with server.role.junit: "
				+ System.getProperty("server.role"));
	}

	/**
	 * Basic T5 object allowing to start web application in testing mode and
	 * then access pages, clink on links and post forms.
	 */
	private PageTester pageTester;

	/**
	 * @return the pageTester
	 */
	public PageTester getPageTester() {
		return pageTester;
	}

	public <T> T getService(Class<T> serviceInterface) {
		return getPageTester().getService(serviceInterface);
	}

	public <T> T getService(Class<T> serviceInterface, String id) {
		return getPageTester().getRegistry().getService(id, serviceInterface);
	}

	@Override
	protected void setUp() throws Exception {
		services = Mocks.getServices();
		services.reset();
		pageTester = new PageTester(T5_APPLICATION_PACKAGE,
				T5_APPLICATION_NAME, T5_WEBAPP_BASE, CoreModule.class);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		services = null;
		pageTester.shutdown();
		pageTester = null;
		super.tearDown();
	}

}

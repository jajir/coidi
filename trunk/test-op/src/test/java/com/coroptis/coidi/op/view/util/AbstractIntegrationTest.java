package com.coroptis.coidi.op.view.util;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.test.PageTester;

import com.coroptis.coidi.core.services.CoreModule;

public abstract class AbstractIntegrationTest extends TestCase {

	protected final static Logger logger = Logger
			.getLogger(AbstractIntegrationTest.class);

	protected final static String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.op.view";

	protected final static String T5_APPLICATION_NAME = "OpView";

	protected final static String T5_WEBAPP_BASE = "src/main/webapp";

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
	protected PageTester getPageTester() {
		return pageTester;
	}

	protected <T> T getService(Class<T> serviceInterface) {
		return getPageTester().getService(serviceInterface);
	}

	protected <T> T getService(Class<T> serviceInterface, String id) {
		return getPageTester().getRegistry().getService(id, serviceInterface);
	}

	@Override
	protected void setUp() throws Exception {
		pageTester = new PageTester(T5_APPLICATION_PACKAGE,
				T5_APPLICATION_NAME, T5_WEBAPP_BASE, CoreModule.class);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		pageTester.shutdown();
		pageTester = null;
		super.tearDown();
	}

}

package com.coroptis.coidi.conf.util;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.test.PageTester;

import com.coroptis.coidi.conf.services.IntegrationTestAppModule;

public abstract class AbstractIntegrationTest extends TestCase {

	protected final static Logger logger = Logger
			.getLogger(AbstractIntegrationTest.class);

	protected final static String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.conf";

	protected final static String T5_APPLICATION_NAME = "Conf";

	protected final static String T5_WEBAPP_BASE = "src/main/webapp";

	static {
		System.setProperty("server.role", "junit");
		System.setProperty("system.property.configuration.directory",
				"not-specified");
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

	@Override
	protected void setUp() throws Exception {
		pageTester = new PageTester(T5_APPLICATION_PACKAGE,
				T5_APPLICATION_NAME, T5_WEBAPP_BASE, IntegrationTestAppModule.class);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		pageTester.shutdown();
		pageTester = null;
		super.tearDown();
	}

}

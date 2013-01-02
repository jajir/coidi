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
package com.coroptis.coidi.core.util;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.test.PageTester;

import com.coroptis.coidi.core.services.IntegrationTestAppModule;

public abstract class AbstractIntegrationTest extends TestCase {

	protected final static Logger logger = Logger
			.getLogger(AbstractIntegrationTest.class);

	protected final static String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.core";

	protected final static String T5_APPLICATION_NAME = "Core";

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

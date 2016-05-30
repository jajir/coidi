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
package com.coroptis.coidi.rp.view.pages;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

public abstract class AbstractJunitTest extends TestCase implements AdditionalBinder {

	protected final static Logger logger = Logger.getLogger(AbstractJunitTest.class);

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
		logger.debug("loading tapestry 5 registry with server.role: " + System.getProperty("server.role"));
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

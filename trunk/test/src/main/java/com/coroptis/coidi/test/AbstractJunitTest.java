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
package com.coroptis.coidi.test;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.services.TapestryModule;

/**
 * 
 * @author jan
 * 
 */
public abstract class AbstractJunitTest extends TestCase implements
		AdditionalBinder {

	private final static Logger logger = Logger
			.getLogger(AbstractJunitTest.class);

	protected Registry registry;

	private final Class<?> moduleClasses[];

	public AbstractJunitTest(Class<?>... moduleClasses) {
		this.moduleClasses = moduleClasses;
	}

	protected <T> T getService(Class<T> serviceInterface) {
		return registry.getService(serviceInterface);
	}

	protected <T> T getService(String serviceId, Class<T> serviceInterface) {
		return registry.getService(serviceId, serviceInterface);
	}

	protected Registry getRegistry() {
		return registry;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AdditionalBinderProvider.setAdditionalBinder(this);
		System.setProperty("server.role", "junit");
		logger.debug("server.role was set to: '"
				+ System.getProperty("server.role") + "'");
		RegistryBuilder builder = new RegistryBuilder();
		builder.add(TapestryModule.class);
		builder.add(moduleClasses);
		registry = builder.build();
		registry.performRegistryStartup();
		logger.debug("T5 registry staterd.");
	}

	@Override
	protected void tearDown() throws Exception {
		AdditionalBinderProvider.setAdditionalBinder(null);
		registry.shutdown();
		registry = null;
		super.tearDown();
	}
}

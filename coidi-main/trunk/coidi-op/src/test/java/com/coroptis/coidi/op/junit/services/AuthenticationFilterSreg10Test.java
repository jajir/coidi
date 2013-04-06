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
package com.coroptis.coidi.op.junit.services;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.impl.AuthenticationProcessorSreg10;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AuthenticationFilterSreg10Test extends AbstractT5JunitTest {

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

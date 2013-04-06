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

import java.util.Date;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AssociationServiceTest extends AbstractT5JunitTest {

	private final static String SERVICE_NAME = "realService";

	private AssociationService service;

	public void testGetTimeToLive() throws Exception {
		/**
		 * How many second could ttl differ from expected time and is still took
		 * as valid.
		 */
		long delta = 5;
		
		long nowInSec = System.currentTimeMillis();
		Date ret = service.getTimeToLive();

		assertNotNull(ret);
		long ttl = ret.getTime();
		assertTrue(ttl - nowInSec < 30 * 60 * 1000 + (1000 * delta));
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(AssociationService.class, AssociationServiceImpl.class)
				.withId(SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = getService(SERVICE_NAME, AssociationService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		service = null;
		super.tearDown();
	}
}

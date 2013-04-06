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

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class NegativeResponseGeneratorTest extends AbstractT5JunitTest {
    private final static String SERVICE_NAME = "realService";

    private NegativeResponseGenerator service;

    public void testSimpleError() throws Exception {
	ErrorResponse ret = service.simpleError("some error");

	assertNotNull(ret);
	assertEquals("some error", ret.getError());
	assertEquals(false, ret.isUrl());
	assertEquals("john@gmail.com", ret.getContact());
	assertEquals(AbstractOpenIdResponse.OPENID_NS_20, ret.getNameSpace());
    }
    
    public void testSimpleError_messageIsNull() throws Exception {
	ErrorResponse ret = service.simpleError(null);

	assertNotNull(ret);
	assertNull(ret.getError());
	assertEquals(false, ret.isUrl());
	assertEquals("john@gmail.com", ret.getContact());
	assertEquals(AbstractOpenIdResponse.OPENID_NS_20, ret.getNameSpace());
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(NegativeResponseGenerator.class, NegativeResponseGeneratorImpl.class).withId(
		SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, NegativeResponseGenerator.class);
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	super.tearDown();
    }
}

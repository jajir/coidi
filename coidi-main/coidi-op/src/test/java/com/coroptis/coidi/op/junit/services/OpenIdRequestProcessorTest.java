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

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.impl.OpenIdRequestProcessorImpl;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.TestUserSession;

public class OpenIdRequestProcessorTest extends AbstractT5JunitTest {
    private final static String SERVICE_NAME = "realService";

    private OpenIdRequestProcessor service;

    public void testProcess_openId_11() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, "some_mode");
	TestUserSession userSession = new TestUserSession();
	AbstractMessage out = new ErrorResponse(true, "some error");
	EasyMock.expect(services.getOpenIdRequestTool().isOpenIdVersion1x(params)).andReturn(true);
	EasyMock.expect(services.getOpenIdDispatcher11().process(params, userSession)).andReturn(
		out);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	services.verify();
	assertEquals(out, ret);
    }

    public void testProcess_openId_20() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_NS, "some_ns");
	params.put(OpenIdDispatcher.OPENID_MODE, "some_mode");
	TestUserSession userSession = new TestUserSession();
	AbstractMessage out = new ErrorResponse(true, "some error");
	EasyMock.expect(services.getOpenIdRequestTool().isOpenIdVersion1x(params)).andReturn(false);
	EasyMock.expect(services.getOpenIdDispatcher20().process(params, userSession)).andReturn(
		out);
	services.replay();
	AbstractMessage ret = service.process(params, userSession);

	services.verify();
	assertEquals(out, ret);
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(OpenIdRequestProcessor.class, OpenIdRequestProcessorImpl.class).withId(
		SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, OpenIdRequestProcessor.class);
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	super.tearDown();
    }
}

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

import java.util.HashSet;
import java.util.Set;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AuthProcNonceTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcNonce.class).withId(SERVICE_NAME);
    }

    public void testProcess_validAssocHandle() throws Exception {
	Set<String> fieldsToSign = new HashSet<String>();
	assertNotNull(service);
	EasyMock.expect(services.getNonceService().createNonce()).andReturn(
		"2013-04-16T00:58:52ZjUuEGNrSH5LTTQ==");
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNull(ret);
	assertEquals("2013-04-16T00:58:52ZjUuEGNrSH5LTTQ==", response.getNonce());
	assertTrue(fieldsToSign.contains("response_nonce"));
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);

	request = new AuthenticationRequest();
	response = new AuthenticationResponse();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	response = null;
	super.tearDown();
    }
}

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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthProcResponse11;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AuthProcResponse11Test extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcResponse11.class).withId(SERVICE_NAME);
    }

    public void testProcess() throws Exception {
	Set<String> fieldsToSign = new HashSet<String>();
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNull(ret);
	assertEquals("http://www.coidi.com/identity/qwe", response.getIdentity());
	assertEquals("http://openid.net/signon/1.1", response.getNameSpace());
	assertEquals("https://sourceforge.net/account/openid_verify.php", response.getReturnTo());
	assertTrue(fieldsToSign.contains("mode"));
	assertTrue(fieldsToSign.contains("identity"));
	assertTrue(fieldsToSign.contains("return_to"));
	services.verify();
    }

    public void testProcess_missingIdentity() throws Exception {
	request.setIdentity(null);
	Set<String> fieldsToSign = new HashSet<String>();
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator().simpleError(
			"Mandatory field 'openid.identity' is missing",
			"http://openid.net/signon/1.1")).andReturn(err);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNotNull(ret);
	assertEquals(err, ret);
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);

	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_SETUP);
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.assoc_handle", "cc5b843b-e375-4640-8f71-38e40b2950a6");
	params.put("openid.return_to", "https://sourceforge.net/account/openid_verify.php");
	request = new AuthenticationRequest(params);
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

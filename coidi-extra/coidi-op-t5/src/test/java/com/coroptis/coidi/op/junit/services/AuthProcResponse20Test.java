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
import com.coroptis.coidi.op.services.impl.AuthProcResponse20;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AuthProcResponse20Test extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcResponse20.class).withId(SERVICE_NAME);
    }

    public void testProcess() throws Exception {
	Set<String> fieldsToSign = new HashSet<String>();
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNull(ret);
	assertEquals("http://www.coidi.com/identity/qwe", response.getIdentity());
	assertEquals("http://www.coidi.com/identity/qwe", response.getClaimedId());
	assertEquals("http://specs.openid.net/auth/2.0", response.getNameSpace());
	assertEquals("https://sourceforge.net/account/openid_verify.php", response.getReturnTo());
	assertTrue(fieldsToSign.contains("op_endpoint"));
	assertTrue(fieldsToSign.contains("return_to"));
	assertTrue(fieldsToSign.contains("claimed_id"));
	assertTrue(fieldsToSign.contains("identity"));
	services.verify();
    }

    public void testProcess_claimendID_identityId_bothAreMissing() throws Exception {
	request.setIdentity(null);
	request.setClaimedId(null);
	Set<String> fieldsToSign = new HashSet<String>();
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNull(ret);
	assertEquals(null, response.getIdentity());
	assertEquals(null, response.getClaimedId());
	assertEquals("http://specs.openid.net/auth/2.0", response.getNameSpace());
	assertEquals("https://sourceforge.net/account/openid_verify.php", response.getReturnTo());
	assertTrue(fieldsToSign.contains("op_endpoint"));
	assertTrue(fieldsToSign.contains("return_to"));
	assertFalse(fieldsToSign.contains("claimed_id"));
	assertFalse(fieldsToSign.contains("identity"));
	services.verify();
    }

    public void testProcess_misssingIdentityId() throws Exception {
	request.setIdentity(null);
	Set<String> fieldsToSign = new HashSet<String>();
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator()
			.buildError("field '",
				AuthenticationResponse.CLAIMED_ID, "' is filled and field '",
				AuthenticationResponse.IDENTITY, "' is empty, this is forbiden state."))
		.andReturn(err);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNotNull(ret);
	assertSame(err, ret);
	services.verify();
    }

    public void testProcess_misssingClaimedId() throws Exception {
	request.setClaimedId(null);
	Set<String> fieldsToSign = new HashSet<String>();
	ErrorResponse err = new ErrorResponse(false);
	EasyMock.expect(
		services.getNegativeResponseGenerator()
			.buildError("field '",
				AuthenticationResponse.CLAIMED_ID, "' is empty and field '",
				AuthenticationResponse.IDENTITY, "' is filled, this is forbiden state."))
		.andReturn(err);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNotNull(ret);
	assertSame(err, ret);
	services.verify();
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AuthenticationProcessor.class);

	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_SETUP);
	params.put("openid.ns", "http://specs.openid.net/auth/2.0");
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.claimed_id", "http://www.coidi.com/identity/qwe");
	params.put("openid.assoc_handle", "cc5b843b-e375-4640-8f71-38e40b2950a6");
	params.put("openid.return_to", "https://sourceforge.net/account/openid_verify.php");
	params.put("openid.realm", "https://sourceforge.net");
	params.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	params.put("openid.sreg.optional", "nickname,email,fullname,country,language,timezone");
	params.put("openid.sreg.policy_url", "http://p.sf.net/sourceforge/privacy");
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

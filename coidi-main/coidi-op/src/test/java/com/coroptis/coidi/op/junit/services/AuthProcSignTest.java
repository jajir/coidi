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

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.google.common.collect.Sets;

public class AuthProcSignTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcSign.class).withId(SERVICE_NAME);
    }

    public void testProcess() throws Exception {
	Set<String> fieldsToSign = new HashSet<String>();
	fieldsToSign.add("mode");
	fieldsToSign.add("noce");
	fieldsToSign.add("identity");
	fieldsToSign.add("claimed_id");
	AssociationBean association = new AssociationBean();
	EasyMock.expect(
		services.getBaseAssociationDao().getByAssocHandle(
			"cc5b843b-e375-4640-8f71-38e40b2950a6")).andReturn(association);
	EasyMock.expect(services.getSigningService().sign(response, association)).andReturn(
		"some_signature");
	services.replay();

	AbstractMessage ret = service.process(request, response, null, fieldsToSign);

	assertNotNull(ret);
	assertSame(response, ret);
	assertEquals("some_signature", response.getSignature());
	assertEquals("http://www.coidi.com/identity/qwe", response.getClaimedId());
	assertEquals("http://specs.openid.net/auth/2.0", response.getNameSpace());
	assertEquals("https://sourceforge.net/account/openid_verify.php", response.getReturnTo());
	fieldsToSign = Sets.newHashSet(response.getSigned().split(","));
	assertTrue(fieldsToSign.contains("mode"));
	assertTrue(fieldsToSign.contains("noce"));
	assertTrue(fieldsToSign.contains("claimed_id"));
	assertTrue(fieldsToSign.contains("identity"));
	services.verify();
    }

    public void testProcess_invalidAssociation() throws Exception {
	Set<String> fieldsToSign = new HashSet<String>();
	EasyMock.expect(
		services.getBaseAssociationDao().getByAssocHandle(
			"cc5b843b-e375-4640-8f71-38e40b2950a6")).andReturn(null);
	services.replay();

	try {
	    service.process(request, response, null, fieldsToSign);
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}

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
	request = new AuthenticationRequest();
	response = new AuthenticationResponse(params);
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	request = null;
	response = null;
	super.tearDown();
    }
}

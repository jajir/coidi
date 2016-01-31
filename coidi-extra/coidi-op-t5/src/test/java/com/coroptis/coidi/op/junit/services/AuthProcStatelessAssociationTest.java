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

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;
import com.coroptis.coidi.op.util.NonceBean;

public class AuthProcStatelessAssociationTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthenticationProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthenticationProcessor.class, AuthProcStateLessAssociation.class).withId(
		SERVICE_NAME);
    }

    public void testProcess_withoutNonce() throws Exception {
	Association association = new AssociationBean();
	association.setAssocHandle("cc5b843b-e375-4640-8f71-38e40b2950a6");
	EasyMock.expect(services.getAssociationService().createStateLessAssociation()).andReturn(
		association);
	services.getBaseAssociationDao().create(association);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, new HashSet<String>());

	assertNull(ret);
	assertEquals("cc5b843b-e375-4640-8f71-38e40b2950a6", response.getAssocHandle());
	services.verify();
    }

    public void testProcess_withNonce() throws Exception {
	response.setNonce("2013-04-16T00:58:52ZjUuEGNrSH5LTTQ==");
	Nonce nonce = new NonceBean();
	EasyMock.expect(services.getBaseNonceDao().createNewInstance()).andReturn(nonce);
	Association association = new AssociationBean();
	association.setAssocHandle("cc5b843b-e375-4640-8f71-38e40b2950a6");
	EasyMock.expect(services.getAssociationService().createStateLessAssociation()).andReturn(
		association);
	services.getBaseAssociationDao().create(association);
	services.replay();

	AbstractMessage ret = service.process(request, response, null, new HashSet<String>());

	assertNull(ret);
	assertEquals("cc5b843b-e375-4640-8f71-38e40b2950a6", response.getAssocHandle());
	assertEquals("2013-04-16T00:58:52ZjUuEGNrSH5LTTQ==", response.getNonce());
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
	params.put("penid.claimed_id", "http://www.coidi.com/identity/qwe");
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

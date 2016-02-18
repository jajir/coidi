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
package com.coroptis.coidi.core.junit.service;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.rp.base.AssociationBean;

public class SigningServiceTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private SigningService signingService;

    public void testSign() throws Exception {
	byte[] bytes = "2012-06-21T19:04:45Z0620".getBytes();
	byte[] signature = "signature".getBytes();
	AuthenticationResponse response = new AuthenticationResponse();
	AssociationBean association = new AssociationBean();
	association.setMacKey("3uAPFsOq3UYQ3v3SeujCZaNclIg=");
	association.setAssociationType(AssociationType.HMAC_SHA1);
	EasyMock.expect(services.getMessageService().extractStringForSign(response, null))
		.andReturn("toSign");
	EasyMock.expect(
		services.getConvertorService().convertToBytes("3uAPFsOq3UYQ3v3SeujCZaNclIg="))
		.andReturn(bytes);
	EasyMock.expect(
		services.getCryptographyService().generateMac(EasyMock.eq(bytes),
			(byte[]) EasyMock.anyObject(), EasyMock.eq(AssociationType.HMAC_SHA1)))
		.andReturn(signature);
	EasyMock.expect(services.getConvertorService().convertToString(signature)).andReturn(
		"signature");
	services.replay();
	String sig = signingService.sign(response, association);

	assertEquals("signature", sig);
	services.verify();
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(SigningService.class, SigningServiceImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	signingService = getService(SERVICE_NAME, SigningService.class);
    }

    @Override
    protected void tearDown() throws Exception {
	signingService = null;
	super.tearDown();
    }
}

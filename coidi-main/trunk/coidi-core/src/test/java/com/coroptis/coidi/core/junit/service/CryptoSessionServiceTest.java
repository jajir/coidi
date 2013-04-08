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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.impl.CryptoSessionServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;
import com.coroptis.coidi.core.util.KeyPair;

public class CryptoSessionServiceTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private CryptoSessionService service;

    public void testGenerateCryptoSession() throws Exception {
	Map<String, String> requestParams = new HashMap<String, String>();
	AssociationRequest request = new AssociationRequest(requestParams);

	services.replay();
	KeyPair ret = service.generateCryptoSession(request);
	
	assertNotNull(ret);
    }

    public void testGenerateCryptoSession2_dhModulo_isNull() throws Exception {
	services.replay();
	KeyPair ret = service.generateCryptoSession(null, BigInteger.TEN);

	assertNotNull(ret);
    }

    public void testGenerateCryptoSession2_dhGen_isNull() throws Exception {
	services.replay();
	KeyPair ret = service.generateCryptoSession(BigInteger.TEN, null);

	assertNotNull(ret);
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(CryptoSessionService.class, CryptoSessionServiceImpl.class)
		.withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, CryptoSessionService.class);
    }

    @Override
    protected void tearDown() throws Exception {
	services.verify();
	service = null;
	super.tearDown();
    }
}

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
package com.coroptis.coidi.rp.integration;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.rp.base.AssociationBean;
import com.coroptis.coidi.rp.util.AbstractIntegrationTest;

public class SigningServiceTest extends AbstractIntegrationTest {

    private Logger logger = Logger.getLogger(SigningServiceTest.class);

    private SigningService signingService;

    public void testBasic() throws Exception {
	assertNotNull(signingService);
    }

    public void testSign() throws Exception {
	Map<String, String> msg = new HashMap<String, String>();
	msg.put("openid.ns", "http://specs.openid.net/auth/2.0");
	msg.put("openid.op_endpoint", "http://server.myid.net/server");
	msg.put("openid.claimed_id", "http://jajir.myid.net/");
	msg.put("openid.response_nonce", "2012-06-21T19:04:45Z0620");
	msg.put("openid.mode", "id_res");
	msg.put("openid.identity", "http://jajir.myid.net/");
	msg.put("openid.return_to", "http://rp.coroptis.com/");
	msg.put("openid.assoc_handle", "f12de3676a78671d");
	msg.put("openid.signed",
		"op_endpoint,claimed_id,identity,return_to,response_nonce,assoc_handle,mode");
	msg.put("openid.sig", "QEdlMNuTjckbVxqh25Mf90nBCe0=");
	AuthenticationResponse response = new AuthenticationResponse(msg);
	AssociationBean association = new AssociationBean();
	association.setMacKey("3uAPFsOq3UYQ3v3SeujCZaNclIg=");
	association.setAssociationType(AssociationType.HMAC_SHA1);
	String sig = signingService.sign(response, association);

	logger.debug("computed: '" + sig + "'");
	logger.debug("obtained: '" + response.getSignature() + "'");
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	signingService = getService(SigningService.class);
    }

    @Override
    protected void tearDown() throws Exception {
	signingService = null;
	super.tearDown();
    }

}

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

import junit.framework.TestCase;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

public class TestJunit extends TestCase {

    public void testSimple() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE,
		CheckAuthenticationRequest.MODE_CHECK_AUTHENTICATION);
	params.put("openid.assoc_handle", "e12ccf51-2484-442c-ba08-61b05be6546f");
	params.put("openid.op_endpoint", "http://www.coidi.com/openid");
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.ns", "http://specs.openid.net/auth/2.0");
	params.put("openid.return_to",
		"https://login.janrain.com/openid/finish?token=6546ccbfa92b4f533c397eff74f8cce1c5aa2f4b");
	params.put("openid.signed",
		"assoc_handle,op_endpoint,identity,return_to,response_nonce,claimed_id");
	params.put("openid.ns.sreg", "http://openid.net/sreg/1.1");
	params.put("openid.response_nonce", "2013-04-14T00:31:31ZivR4+/GQEHI6sw==");
	params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
	params.put("openid.sig", "tPjev37UGi1As2UdcA9T/dMOMZref9ND4dBHwa4gwT4=");

	CheckAuthenticationRequest request = new CheckAuthenticationRequest(params);
	System.out.println(request.getSignature());
	// TODO should be used in test
    }
}

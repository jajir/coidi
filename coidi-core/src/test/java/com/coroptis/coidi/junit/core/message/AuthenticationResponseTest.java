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
package com.coroptis.coidi.junit.core.message;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationResponse;

public class AuthenticationResponseTest {

	@Test
	public void test_isValid() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid.mode", "id_res");
		map.put("openid.identity", "http://karel.is.com");

		AuthenticationResponse response = new AuthenticationResponse(map);
		assertEquals("http://karel.is.com", response.getIdentity());
	}

	@Test
	public void test_setNameSpace() throws Exception {
		AuthenticationResponse response = new AuthenticationResponse();
		assertEquals(AbstractMessage.OPENID_NS_20, response.getNameSpace());

		response.setNameSpace(AbstractMessage.OPENID_NS_11);
		assertEquals(AbstractMessage.OPENID_NS_11, response.getNameSpace());
	}

	@Test
	public void test_values() throws Exception {
		AuthenticationResponse resp = new AuthenticationResponse(new HashMap<String, String>());
		resp.setClaimedId("http://example.com/ident/karel");
		resp.setIdentity("karel");
		resp.setAssocHandle("assoc_handle_example");

		assertEquals("http://example.com/ident/karel", resp.getClaimedId());
		assertEquals("karel", resp.getIdentity());
		assertEquals("assoc_handle_example", resp.getAssocHandle());
	}

}

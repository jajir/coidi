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
package com.coroptis.coidi.core.junit.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.coroptis.coidi.core.message.AuthenticationResponse;

public class AuthenticationResponseTest extends TestCase {

	public void testIsValid() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid.mode", "id_res");
		map.put("openid.identity", "http://karel.is.com");
		
		AuthenticationResponse response = new AuthenticationResponse(map);
		assertEquals("http://karel.is.com", response.getIdentity());
	}


}

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

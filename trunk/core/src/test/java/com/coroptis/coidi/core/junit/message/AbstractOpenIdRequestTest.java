package com.coroptis.coidi.core.junit.message;

import java.util.HashMap;
import java.util.Map;

import com.coroptis.coidi.core.message.AbstractOpenIdRequest;

import junit.framework.TestCase;

public class AbstractOpenIdRequestTest extends TestCase {

	public void testPok() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid.mode", "association");

		AbstractOpenIdRequest pok = new AbstractOpenIdRequest(map);

		assertEquals("association", pok.getMode());
	}
}

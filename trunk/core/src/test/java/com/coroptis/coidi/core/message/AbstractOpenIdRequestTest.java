package com.coroptis.coidi.core.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class AbstractOpenIdRequestTest extends TestCase {

	public void testPok() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid.mode", "association");

		AbstractOpenIdRequest pok = new AbstractOpenIdRequest(map);

		assertEquals("association", pok.getMode());
	}
}

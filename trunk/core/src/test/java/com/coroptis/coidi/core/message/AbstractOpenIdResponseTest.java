package com.coroptis.coidi.core.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class AbstractOpenIdResponseTest extends TestCase {

	public void testCreate() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", "association");

		AbstractOpenIdResponse response = new AbstractOpenIdResponse(map);

		assertEquals("association", response.getMode());
	}

}

package com.coroptis.coidi.core.junit.message;

import com.coroptis.coidi.core.message.AssociationRequest;

import junit.framework.TestCase;

public class AssociationRequestTest extends TestCase {

	public void testMode() throws Exception {
		AssociationRequest request = new AssociationRequest();

		assertEquals("associate", request.getMode());
		assertTrue(request.getMessage().contains("openid.mode"));
		assertTrue(request.getMap().containsKey("openid.mode"));
	}

}

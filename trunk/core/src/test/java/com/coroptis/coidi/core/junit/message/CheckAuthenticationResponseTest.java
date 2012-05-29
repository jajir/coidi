package com.coroptis.coidi.core.junit.message;

import com.coroptis.coidi.core.message.CheckAuthenticationResponse;

import junit.framework.TestCase;

public class CheckAuthenticationResponseTest extends TestCase {

	public void testIsValid() throws Exception {
		CheckAuthenticationResponse response = new CheckAuthenticationResponse();
		assertFalse(response.getIsValid());

		response.setIsValid(true);
		assertTrue(response.getIsValid());

		response.setIsValid(false);
		assertFalse(response.getIsValid());
	}

}

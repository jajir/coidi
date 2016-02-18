package com.coroptis.coidi.junit.rp.services.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.impl.AuthRespOpenId20Verify;
import com.coroptis.coidi.util.AssociationMock;

public class AuthRespOpenId20VerifyTest {

	private AuthenticationResponse authenticationResponse;
	private AssociationMock association;
	private AuthenticationResult authenticationResult;
	private AuthRespOpenId20Verify resp;

	@Test
	public void testName() throws Exception {
		Boolean ret = resp.decode(authenticationResponse, association, authenticationResult);

		assertFalse(ret);
	}

	@Before
	public void setup() {
		authenticationResponse = new AuthenticationResponse();
		authenticationResponse.put("openid.assoc_handle", "67843703-fb87-4fc1-aad0-5ccdac01d739");
		authenticationResponse.put("openid.op_endpoint", "http://localhost:8080/openid");
		association = new AssociationMock();
		authenticationResult = new AuthenticationResult();
		resp = new AuthRespOpenId20Verify();
	}

	@After
	public void tearDown() {
		authenticationResponse = null;
		association = null;
		authenticationResult = null;
		resp = null;
	}
}

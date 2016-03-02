package com.coroptis.coidi.junit.core.message;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;

public class CheckAuthenticationRequestTest {

	private Map<String, String> params;

	private CheckAuthenticationRequest req;

	@Test
	public void test_verifyNonce() throws Exception {
		assertEquals("2016-03-02T15:19:20Z+pqi0lhyAsirsg==", req.getNonce());
		assertEquals("1efbd96c-6112-4ebb-ae79-d451e7a1f455", req.getAssocHandle());
		assertEquals("op_endpoint,identity,return_to,response_nonce,claimed_id,assoc_handle", req.getSigned());
		assertEquals("WOMRLznYVB3dscSLF4APl9xUXck=", req.getSignature());
		assertEquals("http://sso.dev1.test.loc/openid", req.getOpEndpoint());
		assertEquals("broker1", req.getIdentity());
		assertEquals("http://qqq.dev1.rr.loc/sso/examples/example.php", req.getReturnTo());
		assertEquals("broker1", req.getClaimedId());
	}

	@Before
	public void setUp() {
		params = new HashMap<String, String>();
		params.put("openid.ns", "http://specs.openid.net/auth/2.0");
		params.put("openid.mode", "check_authentication");
		params.put("openid.assoc_handle", "1efbd96c-6112-4ebb-ae79-d451e7a1f455");
		params.put("openid.signed", "op_endpoint,identity,return_to,response_nonce,claimed_id,assoc_handle");
		params.put("openid.sig", "WOMRLznYVB3dscSLF4APl9xUXck=");
		params.put("openid.op_endpoint", "http://sso.dev1.test.loc/openid");
		params.put("openid.identity", "broker1");
		params.put("openid.return_to", "http://qqq.dev1.rr.loc/sso/examples/example.php");
		params.put("openid.response_nonce", "2016-03-02T15:19:20Z+pqi0lhyAsirsg==");
		params.put("openid.claimed_id", "broker1");
		req = new CheckAuthenticationRequest(params);
	}

	@After
	public void tearDown() {
		params = null;
	}

}

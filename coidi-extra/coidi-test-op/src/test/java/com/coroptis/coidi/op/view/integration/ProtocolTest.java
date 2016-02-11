package com.coroptis.coidi.op.view.integration;

import java.util.HashMap;
import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.util.MockHttpSession;
import com.coroptis.coidi.op.view.util.AbstractIntegrationDaoTest;

public class ProtocolTest extends AbstractIntegrationDaoTest {

    public void test_openID_setup_without_association_openId11() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.trust_root", "http://peeron.com");
	req.put("openid.identity", "http://www.myid.com/juan/");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.return_to", "http://localhost:8081/somePage");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_11, ret.getNameSpace());
	assertNotNull(ret.get("assoc_handle"));
	assertEquals("http://www.myid.com/juan/", ret.get("identity"));
	assertEquals("http://localhost:8081/somePage", ret.get("return_to"));
	assertEquals("assoc_handle,identity,return_to,response_nonce,mode", ret.get("signed"));
	assertNotNull(ret.get("response_nonce"));
	assertFalse(ret.containsKey("invalidate_handle"));
	assertEquals("id_res", ret.get("mode"));
    }

    public void test_check_authentication_openId11() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.mode", "check_authentication");
	req.put("openid.assoc_handle", "d74d63e6-9db7-47b1-a7f2-ba3e04124a50");
	req.put("openid.identity", "http://www.myid.com/juan/");
	req.put("openid.sig", "wxB3Tl0j+czdLCy2GoataaQ5vLc=");
	req.put("openid.return_to",
		"http://peeron.com/cgi-bin/invcgis/login?openid=1&oic.identity=http://www.coidi.com/identity/prasatko&oic.time=1368959102-cb98caa75b05fd288a01");
	req.put("openid.signed", "assoc_handle,identity,return_to,mode");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_11, ret.getNameSpace());
	assertNotNull(ret.get("invalidate_handle"));
	assertEquals("true", ret.get("is_valid"));
    }

    public void test_checkid_setup_openId20_ok() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.ns", "http://specs.openid.net/auth/2.0");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.realm", "http://www.brick.ie/");
	req.put("openid.assoc_handle", "");
	req.put("openid.return_to", "http://www.brick.ie/openid/authenticate?destination=node%2F92");
	req.put("openid.identity", "http://www.myid.com/prasatko/");
	req.put("openid.claimed_id", "http://www.myid.com/prasatko/");
	req.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	req.put("openid.sreg.required", "nickname,email");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
	assertNotNull(ret.get("assoc_handle"));
	assertEquals("http://www.myid.com/prasatko/", ret.get("identity"));
	assertEquals("http://www.myid.com/prasatko/", ret.get("claimed_id"));
	assertEquals("http://www.brick.ie/openid/authenticate?destination=node%2F92",
		ret.get("return_to"));
	assertEquals(
		"sreg.nickname,assoc_handle,op_endpoint,identity,sreg.email,return_to,response_nonce,claimed_id",
		ret.get("signed"));
	assertEquals("", ret.get("invalidate_handle"));
	assertNotNull(ret.get("response_nonce"));
	assertNotNull(ret.get("sig"));
	assertEquals("id_res", ret.get("mode"));
	assertEquals("cunik", ret.get("sreg.nickname"));
	assertEquals("prase@chliv.cz", ret.get("sreg.email"));
    }

    /**
     * This is case when RP ask for identity and user already select some
     * identity. Selected identity is stored under the key
     * 'users_selected_identity'.
     * 
     * @throws Exception
     */
    public void test_checkid_setup_openId20_identity_select() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.ns", "http://specs.openid.net/auth/2.0");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.realm", "http://www.brick.ie/");
	req.put("openid.assoc_handle", "");
	req.put("openid.return_to", "http://www.brick.ie/openid/authenticate?destination=node%2F92");
	req.put("openid.identity", AuthenticationRequest.IDENTITY_SELECT);
	req.put("openid.claimed_id", AuthenticationRequest.IDENTITY_SELECT);
	req.put(AuthenticationRequest.USERS_SELECTED_IDENTITY, "http://www.myid.com/prasatko/");
	req.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	req.put("openid.sreg.required", "nickname,email");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
	assertNotNull(ret.get("assoc_handle"));
	assertEquals("http://www.myid.com/prasatko/", ret.get("identity"));
	assertEquals("http://www.myid.com/prasatko/", ret.get("claimed_id"));
	assertEquals("http://www.brick.ie/openid/authenticate?destination=node%2F92",
		ret.get("return_to"));
	assertEquals(
		"sreg.nickname,assoc_handle,op_endpoint,identity,sreg.email,return_to,response_nonce,claimed_id",
		ret.get("signed"));
	assertEquals("", ret.get("invalidate_handle"));
	assertNotNull(ret.get("response_nonce"));
	assertNotNull(ret.get("sig"));
	assertEquals("id_res", ret.get("mode"));
	assertEquals("cunik", ret.get("sreg.nickname"));
	assertEquals("prase@chliv.cz", ret.get("sreg.email"));
    }

    public void test_checkid_setup_openId20_identityBelongsToOtherUser() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.ns", "http://specs.openid.net/auth/2.0");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.realm", "http://www.brick.ie/");
	req.put("openid.assoc_handle", "");
	req.put("openid.return_to", "http://www.brick.ie/openid/authenticate?destination=node%2F92");
	req.put("openid.identity", "http://www.myid.com/identity/prasatko/");
	req.put("openid.claimed_id", "http://www.myid.com/identity/prasatko/");
	req.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	req.put("openid.sreg.required", "nickname,email");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
	assertNotNull(ret.get("error"));
	assertEquals("john@yahoo.com", ret.get("contact"));
    }

    public void test_checkid_setup_openId20_invalidIdentity() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.ns", "http://specs.openid.net/auth/2.0");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.realm", "http://www.brick.ie/");
	req.put("openid.assoc_handle", "");
	req.put("openid.return_to", "http://www.brick.ie/openid/authenticate?destination=node%2F92");
	req.put("openid.identity", "http://www.coidi.com/identity/prasatko");
	req.put("openid.claimed_id", "http://www.coidi.com/identity/prasatko");
	req.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	req.put("openid.sreg.required", "nickname,email");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
	// TODO verify that it's error message not exception
    }

    /**
     * FIXME test for extension without identity
     * 
     * @throws Exception
     */

    public void test_checkid_setup_openId20_emptyAssocHandle() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.ns", "http://specs.openid.net/auth/2.0");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.realm", "http://www.brick.ie/");
	req.put("openid.assoc_handle", "");
	req.put("openid.return_to", "http://www.brick.ie/openid/authenticate?destination=node%2F92");
	req.put("openid.identity", "http://www.myid.com/juan/");
	req.put("openid.claimed_id", "http://www.myid.com/juan/");
	req.put("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
	req.put("openid.sreg.required", "nickname,email");

	MockHttpSession userSession = new MockHttpSession();
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
	assertNull(ret.get("openid.invalidate_handle"));
    }

}

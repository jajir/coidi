package com.coroptis.coidi.op.view.integration;

import java.util.HashMap;
import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.view.util.AbstractIntegrationDaoTest;

public class ProtocolTest extends AbstractIntegrationDaoTest {

    public void test_openID_setup_without_association_openId11() throws Exception {
	Map<String, String> req = new HashMap<String, String>();
	req.put("openid.trust_root", "http://peeron.com");
	req.put("openid.identity", "http://www.myid.com/juan/");
	req.put("openid.mode", "checkid_setup");
	req.put("openid.return_to", "http://localhost:8081/somePage");

	UserSessionMock userSession = new UserSessionMock();
	userSession.setLogged(true);
	userSession.setIdUser(2);
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_11, ret.getNameSpace());
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

	UserSessionMock userSession = new UserSessionMock();
	userSession.setLogged(true);
	userSession.setIdUser(2);
	OpenIdRequestProcessor authentication = getService(OpenIdRequestProcessor.class);
	AbstractMessage ret = authentication.process(req, userSession);

	System.out.println(ret.isUrl());
	System.out.println(ret.getMessage());
	assertNotNull(ret);
	assertEquals(AbstractMessage.OPENID_NS_11, ret.getNameSpace());
    }

}

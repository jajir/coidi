package com.coroptis.coidi.op.view.integration;

import org.apache.tapestry5.dom.Document;

import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.services.XrdsService;
import com.coroptis.coidi.op.view.util.AbstractIntegrationTest;

public class StartupTest extends AbstractIntegrationTest {

	public void testStartup() throws Exception {
		XrdsService xrdsService = getService(XrdsService.class);

		assertEquals("http://localhost:8080/userxrds/zdenek",
				xrdsService.getXrdsLocation("zdenek"));
	}

	public void testLogin() throws Exception {
		UserService userService = getService(UserService.class);

		assertNull(userService.login("karel", "kachnicka"));
	}

	public void testAuthentication() throws Exception {
		String auth = "/openid?openid.ns=http://specs.openid.net/auth/2.0&"
				+ "openid.mode=checkid_immediate&"
				+ "openid.identity=http%3A%2F%2Flocalhost%3A8080%2Fuser%2Fjuan&"
				+ "openid.claimed_id=http%3A%2F%2Flocalhost%3A8080%2Fuser%2Fjuan&"
				+ "openid.assoc_handle=073813af-6754-476b-ab7e-c548d34c6c5e&"
				+ "openid.return_to=http%3A%2F%2Flocalhost%3A8081%2F&"
				+ "openid.realm=not+in+use";

		Document doc = getPageTester().renderPage(auth);
		

		assertNotNull(doc);
		System.out.println(doc.getRootElement());
	}

}

package com.coroptis.coidi.op.view.integration;

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

}

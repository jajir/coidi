/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.view.integration;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.util.AbstractIntegrationDaoTest;
import com.google.common.io.Files;

public class StartupTest extends AbstractIntegrationDaoTest {

	public void testLogin() throws Exception {
		UserService userService = getService(UserService.class);

		assertNull(userService.login("karel", "kachnicka"));
	}

	public void testLoadInitialData() throws Exception {
		/**
		 * Following code is copied from OpModule
		 */
		Session session = getService(Session.class);
		for (final String line : Files.readLines(new File(
				"src/main/resources/data.sql"), Charset.forName("UTF-8"))) {
			if (line.length() > 0) {
				logger.debug("executing: " + line);
				session.doWork(new Work() {

					public void execute(Connection connection)
							throws SQLException {
						connection.createStatement().execute(line);
						connection.commit();
					}
				});
			}
		}
	}

	public void testDispatchAuthentication() throws Exception {
		Map<String, String> req = new HashMap<String, String>();
		req.put("openid.ns", "http://specs.openid.net/auth/2.0");
		req.put("openid.identity", "http://localhost:8080/user/juan");
		req.put("openid.claimed_id", "http://localhost:8080/user/juan");
		req.put("openid.mode", "checkid_immediate");
		req.put("openid.realm", "not in use");
		req.put("openid.assoc_handle", "6a4129eb-1336-4970-9ca8-f2d56111eddc");
		req.put("openid.return_to", "http://localhost:8081/somePage");

		OpenIdDispatcher authentication = getService(OpenIdDispatcher.class);
		AbstractMessage ret = authentication.process(req, null);

		assertNotNull(ret);
		System.out.println(ret.isUrl());
		System.out.println(ret.getMessage());
	}
}

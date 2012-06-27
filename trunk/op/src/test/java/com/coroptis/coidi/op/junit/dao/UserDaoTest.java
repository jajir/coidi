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
package com.coroptis.coidi.op.junit.dao;

import com.coroptis.coidi.op.dao.UserDao;
import com.coroptis.coidi.op.entities.User;
import com.coroptis.coidi.op.util.AbstractDaoTest;

public class UserDaoTest extends AbstractDaoTest {
	private UserDao dao;

	public void testLogin() throws Exception {
		User ret = dao.login("Jane", "jane34");

		assertNotNull(ret);
		assertEquals("Jane", ret.getName());
	}

	public void testLogin_notFound() throws Exception {
		User ret = dao.login("Jane", "not_exists");

		assertNull(ret);
	}

	public void testGetUserByName() throws Exception {
		User ret = dao.getUserByName("Jane");

		assertNotNull(ret);
		assertEquals("Jane", ret.getName());
	}

	public void testGetUserByName_notFound() throws Exception {
		User ret = dao.getUserByName("Jane3");

		assertNull(ret);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = getService(UserDao.class);
	}

	@Override
	protected void tearDown() throws Exception {
		dao = null;
		super.tearDown();
	}

}

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

import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.entities.StatelessModeNonce;
import com.coroptis.coidi.op.util.AbstractDaoTest;

public class StatelessModeNonceDaoTest extends AbstractDaoTest {

	private StatelessModeNonceDao dao;

	public void testGetByNonce() throws Exception {
		StatelessModeNonce ret = dao.getByNonce("nonce-string-value");

		assertNotNull(ret);
		assertEquals("mac_key_value", ret.getMacKey());
	}

	public void testGetByNonce_notFound() throws Exception {
		StatelessModeNonce ret = dao.getByNonce("no_such_nonce");

		assertNull(ret);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = getService(StatelessModeNonceDao.class);
	}

	@Override
	protected void tearDown() throws Exception {
		dao = null;
		super.tearDown();
	}

}

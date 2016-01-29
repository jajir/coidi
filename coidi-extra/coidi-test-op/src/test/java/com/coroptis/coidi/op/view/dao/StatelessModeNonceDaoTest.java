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
package com.coroptis.coidi.op.view.dao;

import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.view.util.AbstractDaoTest;

public class StatelessModeNonceDaoTest extends AbstractDaoTest {

    private BaseNonceDao dao;

    public void testGetByNonce() throws Exception {
	Nonce ret = dao.getByNonce("nonce-string-value");

	assertNotNull(ret);
    }

    public void testGetByNonce_notFound() throws Exception {
	Nonce ret = dao.getByNonce("no_such_nonce");

	assertNull(ret);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	dao = getService(BaseNonceDao.class);
    }

    @Override
    protected void tearDown() throws Exception {
	dao = null;
	super.tearDown();
    }

}

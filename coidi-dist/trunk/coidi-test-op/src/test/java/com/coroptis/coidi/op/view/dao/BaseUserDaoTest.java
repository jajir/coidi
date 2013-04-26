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

import com.coroptis.coidi.op.dao.BaseUserDao;
import com.coroptis.coidi.op.entities.User;
import com.coroptis.coidi.op.view.entities.UserImpl;
import com.coroptis.coidi.op.view.util.AbstractDaoTest;

public class BaseUserDaoTest extends AbstractDaoTest {

    private BaseUserDao dao;

    public void testGetById() throws Exception {
	UserImpl ret = (UserImpl) dao.getById(1);

	assertNotNull(ret);
	assertEquals("Jane", ret.getName());
    }

    public void testGetById_noSuchUser() throws Exception {
	User ret = dao.getById(786876);

	assertNull(ret);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	dao = getService(BaseUserDao.class);
    }

    @Override
    protected void tearDown() throws Exception {
	dao = null;
	super.tearDown();
    }

}

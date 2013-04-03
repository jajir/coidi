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

import java.util.List;

import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.view.util.AbstractDaoTest;

public class IdentityDaoTest extends AbstractDaoTest {

    IdentityDao identityDao;

    public void testGetCount() throws Exception {
	Integer ret = identityDao.getCount();

	assertNotNull(ret);
	assertEquals(Integer.valueOf(3), ret);
    }

    public void testGetChunk_allRecords() throws Exception {
	List<Identity> ret = identityDao.getChunk(null, null);

	assertNotNull(ret);
	assertEquals(3, ret.size());
    }

    public void testGetChunk_second_record() throws Exception {
	List<Identity> ret = identityDao.getChunk(1, 1);

	assertNotNull(ret);
	assertEquals(1, ret.size());
	assertEquals("juan", ret.get(0).getIdIdentity());
    }

    public void testGetChunk_startIndex_is_null() throws Exception {
	List<Identity> ret = identityDao.getChunk(null, 1);

	assertNotNull(ret);
	assertEquals(1, ret.size());
	assertEquals("jane", ret.get(0).getIdIdentity());
    }

    public void testGetChunk_endIndex_is_null() throws Exception {
	List<Identity> ret = identityDao.getChunk(2, null);

	assertNotNull(ret);
	assertEquals(1, ret.size());
	assertEquals("juane", ret.get(0).getIdIdentity());
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	identityDao = getService(IdentityDao.class);
    }

    @Override
    protected void tearDown() throws Exception {
	identityDao = null;
	super.tearDown();
    }
}

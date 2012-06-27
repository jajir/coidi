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

import java.util.Date;

import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationImpl;
import com.coroptis.coidi.op.util.AbstractDaoTest;

public class AssociationDaoTest extends AbstractDaoTest {

	AssociationDao associationService;

	public void testGetIdentityByName() throws Exception {
		AssociationImpl ret = associationService
				.getByAssocHandle("bbbb-bbbb-bbbb-bbbb");

		assertNotNull(ret);
	}

	public void testGetIdentityByName_notExists() throws Exception {
		AssociationImpl ret = associationService
				.getByAssocHandle("bbbb-bbbb-bbbb-blee");

		assertNull(ret);
	}

	public void testCreate() throws Exception {
		AssociationImpl assoc = new AssociationImpl();
		assoc.setAssocHandle("aaaa-aaaa-aaaa-aaaa");
		assoc.setAssociationType(AssociationType.HMAC_SHA1);
		assoc.setExpiredIn(new Date());
		assoc.setMacKey("1234567890123456789012345678901234567890");
		assoc.setSessionType(SessionType.DH_SHA1);

		associationService.create(assoc);

		assertEquals(getConnection().getRowCount("association"), getDataSet()
				.getTable("association").getRowCount() + 1);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		associationService = getService(AssociationDao.class);
	}

	@Override
	protected void tearDown() throws Exception {
		associationService = null;
		super.tearDown();
	}
}

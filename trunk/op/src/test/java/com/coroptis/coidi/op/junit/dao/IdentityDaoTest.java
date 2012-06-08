package com.coroptis.coidi.op.junit.dao;

import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.util.AbstractDaoTest;

public class IdentityDaoTest extends AbstractDaoTest {

	IdentityDao identityDao;

	public void testGetIdentityByName_notExists() throws Exception {
		Identity ret = identityDao.getIdentityByName("brekeke");

		assertNull(ret);
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

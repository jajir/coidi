package com.coroptis.coidi.op.view.dao;

import com.coroptis.coidi.op.view.entities.Identity;
import com.coroptis.coidi.op.view.services.IdentityService;
import com.coroptis.coidi.op.view.util.AbstractIntegrationDaoTest;

public class IdentityServiceTest extends AbstractIntegrationDaoTest {

	IdentityService identityService;

	public void testGetIdentityByName_notExists() throws Exception {
		Identity ret = identityService.getIdentityByName("brekeke");

		assertNull(ret);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		identityService = getService(IdentityService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		identityService = null;
		super.tearDown();
	}
}

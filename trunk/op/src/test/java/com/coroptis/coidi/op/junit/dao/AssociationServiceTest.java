package com.coroptis.coidi.op.junit.dao;

import java.util.Date;

import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationImpl;
import com.coroptis.coidi.op.util.AbstractDaoTest;

public class AssociationServiceTest extends AbstractDaoTest {

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

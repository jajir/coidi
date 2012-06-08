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

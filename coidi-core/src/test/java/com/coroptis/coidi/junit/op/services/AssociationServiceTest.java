package com.coroptis.coidi.junit.op.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.CryptoService;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.util.AssociationMock;

public class AssociationServiceTest {

	private AssociationService associationService;

	private AssociationMock association;

	private final static String ASSOC_HANDLE = "salasl";

	private Calendar calendar;

	private BaseAssociationDao baseAssociationDao;

	private CryptoService cryptoService;

	private AssociationTool associationTool;

	private ConvertorService convertorService;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(associationService);
	}

	@Test
	public void test_isValid() throws Exception {
		EasyMock.expect(baseAssociationDao.getByAssocHandle(ASSOC_HANDLE)).andReturn(association);
		EasyMock.replay(baseAssociationDao, cryptoService, associationTool, convertorService);
		boolean ret = associationService.isValid(ASSOC_HANDLE);

		assertTrue(ret);
		EasyMock.verify(baseAssociationDao, cryptoService, associationTool, convertorService);
	}

	@Test
	public void test_isValid_associationExpired() throws Exception {
		calendar.add(Calendar.MINUTE, -30);
		association.setExpiredIn(calendar.getTime());
		EasyMock.expect(baseAssociationDao.getByAssocHandle(ASSOC_HANDLE)).andReturn(association);
		EasyMock.replay(baseAssociationDao, cryptoService, associationTool, convertorService);
		boolean ret = associationService.isValid(ASSOC_HANDLE);

		assertFalse(ret);
		EasyMock.verify(baseAssociationDao, cryptoService, associationTool, convertorService);
	}

	@Test
	public void test_isValid_missingAssociation() throws Exception {
		EasyMock.expect(baseAssociationDao.getByAssocHandle(ASSOC_HANDLE)).andReturn(null);
		EasyMock.replay(baseAssociationDao, cryptoService, associationTool, convertorService);
		boolean ret = associationService.isValid(ASSOC_HANDLE);

		assertFalse(ret);
		EasyMock.verify(baseAssociationDao, cryptoService, associationTool, convertorService);
	}

	@Test
	public void test_isValid_handleIsNull() throws Exception {
		EasyMock.replay(baseAssociationDao, cryptoService, associationTool, convertorService);
		boolean ret = associationService.isValid(null);

		assertFalse(ret);
		EasyMock.verify(baseAssociationDao, cryptoService, associationTool, convertorService);
	}

	@Before
	public void setUp() {
		baseAssociationDao = EasyMock.createMock(BaseAssociationDao.class);
		cryptoService = EasyMock.createMock(CryptoService.class);
		associationTool = EasyMock.createMock(AssociationTool.class);
		convertorService = EasyMock.createMock(ConvertorService.class);
		associationService = new AssociationServiceImpl(baseAssociationDao, cryptoService, associationTool, convertorService);
		association = new AssociationMock();
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10);
		association.setExpiredIn(calendar.getTime());
		association.setAssocHandle(ASSOC_HANDLE);
	}

	@After
	public void tearDown() {
		associationService = null;
		baseAssociationDao = null;
		cryptoService = null;
		associationTool = null;
		convertorService = null;
		association = null;
		calendar = null;
	}

}
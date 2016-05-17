package com.coroptis.coidi.junit.rp.services.impl;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.rp.services.NonceStorage;
import com.coroptis.coidi.rp.services.impl.NonceServiceImpl;

public class NonceServiceTest {

	private final static String nonce = "2016-05-17T19:19:48Zhello";
	private final static int expirationInMinutes = 10;
	
	private NonceServiceImpl nonceService;
	private NonceTool nonceTool;
	private NonceStorage nonceStorage;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(nonceService);
		assertNotNull(nonceTool);
		assertNotNull(nonceStorage);
	}

	@Test
	public void test_isNonceValid() throws Exception {
		EasyMock.expect(nonceTool.isNonceExpired(nonce, expirationInMinutes)).andReturn(false);
		EasyMock.expect(nonceStorage.isExists(nonce)).andReturn(false);
		nonceStorage.storeNonce(nonce);
		EasyMock.replay(nonceTool, nonceStorage);
		final boolean ret = nonceService.isNonceValid(nonce, 10);

		assertTrue(ret);
		EasyMock.verify(nonceTool, nonceStorage);
	}

	@Test
	public void test_isNonceValid_alreadyStored() throws Exception {
		EasyMock.expect(nonceTool.isNonceExpired(nonce, expirationInMinutes)).andReturn(false);
		EasyMock.expect(nonceStorage.isExists(nonce)).andReturn(true);
		EasyMock.replay(nonceTool, nonceStorage);
		final boolean ret = nonceService.isNonceValid(nonce, 10);

		assertFalse(ret);
		EasyMock.verify(nonceTool, nonceStorage);
	}

	@Test
	public void test_isNonceValid_expiredNonce() throws Exception {
		EasyMock.expect(nonceTool.isNonceExpired(nonce, expirationInMinutes)).andReturn(true);
		EasyMock.replay(nonceTool, nonceStorage);
		final boolean ret = nonceService.isNonceValid(nonce, 10);

		assertFalse(ret);
		EasyMock.verify(nonceTool, nonceStorage);
	}

	@Before
	public void setup() {
		nonceTool = EasyMock.createMock(NonceTool.class);
		nonceStorage = EasyMock.createMock(NonceStorage.class);
		nonceService = new NonceServiceImpl(nonceTool, nonceStorage);
	}

	@After
	public void tearDown() {
		nonceService = null;
		nonceTool = null;
		nonceStorage = null;
	}

}

package com.coroptis.coidi.junit.rp.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.rp.services.impl.NonceStoreInMemory;

public class NonceStoreInMemoryTest {

	NonceStoreInMemory nonceStore;

	@Test
	public void test_missing_nonce() throws Exception {
		nonceStore.storeNonce("slepice");
		Thread.sleep(1000 * 2);
		assertFalse(nonceStore.isExists("osel"));
	}

	@Test
	public void test_insert_and_verify() throws Exception {
		nonceStore.storeNonce("ahoj");

		assertTrue(nonceStore.isExists("ahoj"));
	}

	@Before
	public void setup() {
		nonceStore = new NonceStoreInMemory(1);
	}

	@After
	public void tearDown() {
		nonceStore.stopTimer();
		nonceStore = null;
	}

}

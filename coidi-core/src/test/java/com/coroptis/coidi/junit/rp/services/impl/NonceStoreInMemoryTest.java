package com.coroptis.coidi.junit.rp.services.impl;
import static org.junit.Assert.*;
import org.junit.Test;

import com.coroptis.coidi.rp.services.impl.NonceStoreInMemory;

public class NonceStoreInMemoryTest {
	
	@Test
	public void test_start() throws Exception {
		NonceStoreInMemory nonceStore = new NonceStoreInMemory(1);
		nonceStore.storeNonce("slepice");
		Thread.sleep(1000* 2);
		assertFalse(nonceStore.isExists("osel"));
	}
	
}

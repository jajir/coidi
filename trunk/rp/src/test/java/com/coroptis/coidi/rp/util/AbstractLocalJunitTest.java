package com.coroptis.coidi.rp.util;

import com.coroptis.coidi.test.AbstractJunitTest;

public abstract class AbstractLocalJunitTest extends AbstractJunitTest {

	protected Services services;

	public AbstractLocalJunitTest() {
		super(JunitAppModule.class);
	}

	@Override
	protected void setUp() throws Exception {
		services = Services.getServices();
		services.reset();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		services = null;
		super.tearDown();
	}
}

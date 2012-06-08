package com.coroptis.coidi.op.junit.services;

import java.util.Date;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.util.BaseJunitTest;

public class AssociationServiceTest extends BaseJunitTest {

	private final static String SERVICE_NAME = "realService";

	private AssociationService service;

	public void testGetTimeToLive() throws Exception {
		/**
		 * How many second could ttl differ from expected time and is still took
		 * as valid.
		 */
		long delta = 5;
		
		long nowInSec = System.currentTimeMillis();
		Date ret = service.getTimeToLive();

		assertNotNull(ret);
		long ttl = ret.getTime();
		assertTrue(ttl - nowInSec < 30 * 60 * 1000 + (1000 * delta));
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(AssociationService.class, AssociationServiceImpl.class)
				.withId(SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = getService(SERVICE_NAME, AssociationService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		service = null;
		super.tearDown();
	}
}

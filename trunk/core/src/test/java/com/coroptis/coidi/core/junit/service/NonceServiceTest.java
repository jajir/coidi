package com.coroptis.coidi.core.junit.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.impl.NonceServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;

public class NonceServiceTest extends BaseJunitTest {

	private final Logger logger = Logger.getLogger(NonceServiceTest.class);

	private final static String SERVICE_NAME = "realService";

	private NonceService nonceService;

	public void testVerifyNonce_false() throws Exception {
		services.replay();
		
		assertFalse(nonceService.verifyNonce("2012-06-20T21:27:13ZrCqe4J", 30));
	}

	public void testVerifyNonce_true() throws Exception {
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(
				"yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'");
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String nonce = isoDateFormatter.format(new Date()) + "rCqe4J";
		logger.debug("nonce: " + nonce);
		services.replay();
		
		assertTrue(nonceService.verifyNonce(nonce, 30));
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(NonceService.class, NonceServiceImpl.class).withId(
				SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		nonceService = getService(SERVICE_NAME, NonceService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		services.verify();
		nonceService = null;
		super.tearDown();
	}
}

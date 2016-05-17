package com.coroptis.coidi.junit.op.services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.core.services.impl.NonceToolImpl;
import com.google.common.base.Preconditions;

/**
 * Verify that date tools works correctly.
 * 
 * @author jirout
 *
 */
public class NonceToolTest {

	private final static Logger logger = LoggerFactory.getLogger(NonceToolTest.class);

	private final static String HASH = "hello";

	private NonceToolImpl nonceTool;

	private ConvertorService convertorService;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(nonceTool);
		assertNotNull(convertorService);
	}

	@Test
	public void test_createNonce() throws Exception {
		EasyMock.expect(convertorService.convertToString((byte[]) EasyMock.anyObject())).andReturn(HASH);
		EasyMock.replay(convertorService);
		String nonce = nonceTool.createNonce();

		logger.debug(nonce);
		assertTrue(nonce.endsWith(HASH));
		final Date date = extractDate(nonce, HASH);
		assertDatesAlmostEqual(date, new Date());
		EasyMock.verify(convertorService);
	}

	@Test
	public void test_isNonceExpired_notExpired() throws Exception {
		assertFalse(nonceTool.isNonceExpired(createNonce(new Date(), HASH), 1));
	}

	@Test
	public void test_isNonceExpired_expired_1minute() throws Exception {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.add(Calendar.MINUTE, -10);

		assertTrue(nonceTool.isNonceExpired(createNonce(calendar.getTime(), HASH), 1));
	}

	@Before
	public void setup() {
		convertorService = EasyMock.createMock(ConvertorService.class);
		nonceTool = new NonceToolImpl(convertorService);
	}

	@After
	public void tearDown() {
		convertorService = null;
		nonceTool = null;
	}

	Date extractDate(final String nonceRough, final String hash) throws ParseException {
		final String nonce = nonceRough.substring(0, nonceRough.length() - hash.length());
		final SimpleDateFormat isoDateFormatter = new SimpleDateFormat(NonceTool.ISO_DATETIME_FORMAT);
		final TimeZone timeZone = TimeZone.getTimeZone("UTC");
		isoDateFormatter.setTimeZone(timeZone);
		return isoDateFormatter.parse(Preconditions.checkNotNull(nonce, "nonce is null"));
	}

	private String createNonce(Date date, String hash) {
		final SimpleDateFormat isoDateFormatter = new SimpleDateFormat(NonceTool.ISO_DATETIME_FORMAT);
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return isoDateFormatter.format(date) + hash;
	}

	/**
	 * When difference between two dates is smaller than 100 ms than return true
	 * otherwise return false.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	private final void assertDatesAlmostEqual(final Date date1, final Date date2) {
		logger.debug("Comparing '{}' and '{}'", date1, date2);
		assertTrue("Dates are too different ", Math.abs(date1.getTime() - date2.getTime()) <= 2000);
	}

}

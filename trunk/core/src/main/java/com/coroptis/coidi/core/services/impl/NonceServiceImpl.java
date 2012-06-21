package com.coroptis.coidi.core.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceService;
import com.google.common.base.Preconditions;

/**
 * 
 * @author jan
 * 
 */
public class NonceServiceImpl implements NonceService {

	@Inject
	private Logger logger;

	private final SecureRandom random;

	@Inject
	private ConvertorService convertorService;

	public NonceServiceImpl() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
	}

	@Override
	public Date extractDate(final String nonce) {
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(
				ISO_DATETIME_FORMAT);
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return isoDateFormatter.parse(Preconditions.checkNotNull(nonce,
					"nonce"));
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public boolean verifyNonce(final String nonce,
			final Integer expirationMinutes) {
		Date now = new Date();
		Date nonceDateTime = extractDate(nonce);

		if (nonceDateTime == null) {
			logger.info("Nonce is invalid");
			return false;
		}

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(nonceDateTime);
		calendar.add(Calendar.MINUTE, expirationMinutes);
		if (calendar.getTime().before(now)) {
			logger.debug("Nonce expired. Because now is '" + now
					+ "' and nonce is '" + calendar.getTime() + "'");
			return false;
		}

		return true;
	}

	@Override
	public String createNonce() {
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(
				ISO_DATETIME_FORMAT);
		return isoDateFormatter.format(new Date()) + generateCrumb();
	}

	private String generateCrumb() {
		byte[] b = new byte[10];
		random.nextBytes(b);
		return convertorService.convertToString(b);
	}
}

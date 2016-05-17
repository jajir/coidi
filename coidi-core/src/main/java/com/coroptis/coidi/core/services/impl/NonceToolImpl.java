package com.coroptis.coidi.core.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceTool;
import com.google.common.base.Preconditions;

public class NonceToolImpl implements NonceTool {

	private Logger logger = LoggerFactory.getLogger(NonceToolImpl.class);

	private final SecureRandom random;

	private final ConvertorService convertorService;

	public NonceToolImpl(final ConvertorService convertorService) {
		this.convertorService = Preconditions.checkNotNull(convertorService);
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new CoidiException(e.getMessage(), e);
		}
	}

	@Override
	public boolean isNonceExpired(final String nonce, final Integer expirationInMinutes) {
		Date now = new Date();
		Date nonceDateTime = extractDate(nonce);

		if (nonceDateTime == null) {
			logger.info("Nonce is invalid");
			return true;
		}

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(nonceDateTime);
		calendar.add(Calendar.MINUTE, expirationInMinutes);
		if (calendar.getTime().before(now)) {
			logger.debug("Nonce expired. Because now is '" + now + "' and nonce is '" + calendar.getTime() + "'");
			return true;
		}

		return false;
	}

	Date extractDate(final String nonce) {
		final SimpleDateFormat isoDateFormatter = new SimpleDateFormat(ISO_DATETIME_FORMAT);
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return isoDateFormatter.parse(Preconditions.checkNotNull(nonce, "nonce is null"));
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public String createNonce() {
		final SimpleDateFormat isoDateFormatter = new SimpleDateFormat(NonceTool.ISO_DATETIME_FORMAT);
		/**
		 * According to OpenID specification nonce date is in UTC timezone
		 */
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return isoDateFormatter.format(new Date()) + generateCrumb();
	}

	private String generateCrumb() {
		byte[] b = new byte[10];
		random.nextBytes(b);
		return convertorService.convertToString(b);
	}

}

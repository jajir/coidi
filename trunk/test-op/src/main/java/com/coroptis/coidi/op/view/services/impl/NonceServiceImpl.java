package com.coroptis.coidi.op.view.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.coroptis.coidi.op.view.services.NonceService;
import com.coroptis.coidi.op.view.utils.Crypto;

public class NonceServiceImpl implements NonceService {

	private final static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	// FIXME it's not thread safe
	private final static SimpleDateFormat ISO_DATE_FORMATTER = new SimpleDateFormat(
			ISO_DATETIME_FORMAT);

	private final SecureRandom random;

	public NonceServiceImpl() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
	}

	public String generateCrumb() {
		byte[] b = new byte[10];
		random.nextBytes(b);
		return Crypto.convertToString(b);
	}

	@Override
	public String createNonce() {
		return ISO_DATE_FORMATTER.format(new Date()) + generateCrumb();
	}

}

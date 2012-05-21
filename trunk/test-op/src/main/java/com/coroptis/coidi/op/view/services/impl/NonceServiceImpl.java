package com.coroptis.coidi.op.view.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.op.view.services.NonceService;

public class NonceServiceImpl implements NonceService {

	private final static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	private final SecureRandom random;

	@Inject
	private ConvertorService convertorService;

	public NonceServiceImpl() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
	}

	private String generateCrumb() {
		byte[] b = new byte[10];
		random.nextBytes(b);
		return convertorService.convertToString(b);
	}

	@Override
	public String createNonce() {
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(
				ISO_DATETIME_FORMAT);
		return isoDateFormatter.format(new Date()) + generateCrumb();
	}

}

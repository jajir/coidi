package com.coroptis.coidi.core.services;

import java.util.Date;

public interface NonceService {

	final static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	/**
	 * Extract a date from a nonce.
	 * 
	 * @param nonce
	 * @return date from nonce
	 */
	Date extractDate(String nonce);

	/**
	 * Verifies whether a nonce is valid or not.
	 * 
	 * @param nonce
	 *            nonce to verify
	 * @param expirationMinutes
	 *            minutes how long the nonce is valid
	 * @return true if nonce is valide, false otherwise
	 */
	boolean verifyNonce(String nonce, Integer expirationMinutes);

	String createNonce();

}
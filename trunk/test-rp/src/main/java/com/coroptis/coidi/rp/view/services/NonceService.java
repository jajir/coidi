package com.coroptis.coidi.rp.view.services;

import java.util.Date;

public interface NonceService {

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
	 * @param nonce nonce to verify
	 * @param expirationMinutes minutes how long the nonce is valid
	 * @return true if nonce is valide, false otherwise
	 */
	boolean verifyNonce(String nonce, int expirationMinutes);

}
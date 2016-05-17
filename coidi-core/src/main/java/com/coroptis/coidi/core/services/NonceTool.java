package com.coroptis.coidi.core.services;

public interface NonceTool {

	final static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	/**
	 * Verifies whether a nonce is valid or not base on expiration.
	 * 
	 * @param nonce
	 *            required nonce to verify
	 * @param expirationInMinutes
	 *            required minutes how long the nonce is valid
	 * @return <code>true</code> if nonce is valid otherwise <code>false</code>
	 */
	boolean isNonceExpired(String nonce, Integer expirationInMinutes);

	String createNonce();
	
}

package com.coroptis.coidi.rp.services;

/**
 * Nonce is string key that allows to assure replaying party that positive
 * response from authorization procedure comes exactly one. When one nonce comes
 * second time then replaying party should refuse to authorize request.
 * 
 * @author jan
 * 
 */
public interface NonceDao {
	
	public static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	/**
	 * Persist nonce.
	 * 
	 * @param nonce
	 *            required nonce to store
	 */
	void storeNonce(String nonce);

	/**
	 * Provide information if nonce already stored or not.
	 * 
	 * @param nonce
	 *            required string nonce
	 * @return
	 */
	boolean isExists(String nonce);

	/**
	 * Each nonce valid just for limited time. Nonce contains time when was
	 * generated on server. When this time older than some period then nonce is
	 * invalid and there is no need to store it so it could be removed. Time
	 * when
	 * <p>
	 * As period when nonce is consider as valid is usually used 1 hour.
	 * </p>
	 * <p>
	 * This method should be periodically called by application scheduler.
	 * </p>
	 */
	void removeOldNonces();

}

package com.coroptis.coidi;

/**
 * Generic exception. All non runtime exception will be encapsulate in this
 * exception hierarchy from.
 * 
 * @author jan
 * 
 */
public class CoidiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoidiException(String message) {
		super(message);
	}

	public CoidiException(String message, Throwable throwable) {
		super(message, throwable);
	}

}

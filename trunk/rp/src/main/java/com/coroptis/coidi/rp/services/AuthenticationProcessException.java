package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.CoidiException;

/**
 * This runtime exception is throws from discovery process and authentication
 * request processing. Exception contains message that should be presented to
 * end user.
 * 
 * @author jan
 * 
 */
public class AuthenticationProcessException extends CoidiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationProcessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public AuthenticationProcessException(String message) {
		super(message);
	}

}

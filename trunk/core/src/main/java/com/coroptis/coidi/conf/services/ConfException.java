package com.coroptis.coidi.conf.services;

/**
 * Exception related to problems with configuration.
 * 
 * @author jan
 * 
 */
public class ConfException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfException(String message) {
		super(message);
	}

}

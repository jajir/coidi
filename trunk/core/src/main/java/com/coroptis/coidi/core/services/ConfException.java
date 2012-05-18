package com.coroptis.coidi.core.services;

import com.coroptis.coidi.CoidiException;

/**
 * Exception related to problems with configuration.
 * 
 * @author jan
 * 
 */
public class ConfException extends CoidiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfException(String message) {
		super(message);
	}

}

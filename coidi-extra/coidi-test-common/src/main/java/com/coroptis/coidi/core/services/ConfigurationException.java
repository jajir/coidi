package com.coroptis.coidi.core.services;

/**
 * 
 * @author jan
 *
 */
public class ConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

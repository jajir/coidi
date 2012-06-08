package com.coroptis.coidi.op.services;

import com.coroptis.coidi.CoidiException;

/**
 * 
 * @author jan
 * 
 */
public interface XrdsService {

	/**
	 * For given user name produce XRDS document.
	 * 
	 * @param user
	 *            required user name
	 * @return XRDS document
	 * @throws CoidiException
	 *             is throws at least when user doesn't exists
	 */
	String getDocument(String user) throws CoidiException;

	String getXrdsLocation(String userName);
}

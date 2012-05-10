package com.coroptis.coidi.op.view.utils;

import org.apache.log4j.Logger;

import com.coroptis.coidi.op.view.entities.User;

/**
 * Object holding user session data.
 * 
 * @author jan
 * 
 */
public class UserSession {

	private final static Logger logger = Logger.getLogger(UserSession.class);

	private User user;

	public boolean isUserLogged() {
		return user != null;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
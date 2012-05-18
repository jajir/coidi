package com.coroptis.coidi.op.view.utils;

import com.coroptis.coidi.op.view.entities.User;

public class UserSession {

	private User user;

	public boolean isLogged() {
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
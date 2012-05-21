package com.coroptis.coidi.op.view.utils;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.view.entities.User;

public class UserSession {

	private User user;

	private AuthenticationRequest authenticationRequest;

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

	/**
	 * @return the authenticationRequest
	 */
	public AuthenticationRequest getAuthenticationRequest() {
		return authenticationRequest;
	}

	/**
	 * @param authenticationRequest
	 *            the authenticationRequest to set
	 */
	public void setAuthenticationRequest(
			AuthenticationRequest authenticationRequest) {
		this.authenticationRequest = authenticationRequest;
	}

}
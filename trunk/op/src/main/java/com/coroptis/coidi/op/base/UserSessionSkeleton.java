package com.coroptis.coidi.op.base;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.User;

public interface UserSessionSkeleton {

	boolean isLogged();

	/**
	 * 
	 * @return the user
	 */
	User getUser();

	/**
	 * @return the idUser
	 */
	Integer getIdUser();

	void setAuthenticationRequest(AuthenticationRequest authenticationRequest);

}
package com.coroptis.coidi.op.view.integration;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;

public class UserSessionMock implements UserSessionSkeleton {

    private boolean isLogged;

    private Integer idUser;

    private AuthenticationRequest authenticationRequest;

    @Override
    public boolean isLogged() {
	return isLogged;
    }

    @Override
    public Integer getIdUser() {
	return idUser;
    }

    @Override
    public void setAuthenticationRequest(AuthenticationRequest authenticationRequest) {
    }

    /**
     * @return the authenticationRequest
     */
    public AuthenticationRequest getAuthenticationRequest() {
	return authenticationRequest;
    }

    /**
     * @param isLogged
     *            the isLogged to set
     */
    public void setLogged(boolean isLogged) {
	this.isLogged = isLogged;
    }

    /**
     * @param idUser
     *            the idUser to set
     */
    public void setIdUser(Integer idUser) {
	this.idUser = idUser;
    }

}

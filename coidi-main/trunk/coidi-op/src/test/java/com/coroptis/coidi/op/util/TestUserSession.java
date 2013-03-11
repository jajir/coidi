package com.coroptis.coidi.op.util;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.User;

public class TestUserSession implements UserSessionSkeleton {

    private User user;

    private Integer idUser;

    /**
     * @return the user
     */
    @Override
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
     * @return the idUser
     */
    @Override
    public Integer getIdUser() {
	return idUser;
    }

    /**
     * @param idUser
     *            the idUser to set
     */
    public void setIdUser(Integer idUser) {
	this.idUser = idUser;
    }

    @Override
    public boolean isLogged() {
	return user != null;
    }

    @Override
    public void setAuthenticationRequest(AuthenticationRequest authenticationRequest) {
	//
    }

}

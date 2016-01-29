/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.view.utils;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.User;
import com.coroptis.coidi.op.view.entities.UserImpl;
import com.coroptis.coidi.op.view.services.UserService;

public class UserSession implements UserSessionSkeleton {

    @Inject
    private UserService userService;

    private Integer idUser;

    private AuthenticationRequest authenticationRequest;

    @Override
    public boolean isLogged() {
	return idUser != null;
    }

    public UserImpl getUser() {
	if (idUser == null) {
	    return null;
	} else {
	    return userService.getById(idUser);
	}
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.idUser = user.getIdUser();
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
    @Override
    public void setAuthenticationRequest(AuthenticationRequest authenticationRequest) {
	this.authenticationRequest = authenticationRequest;
    }

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

}
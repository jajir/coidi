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
package com.coroptis.coidi.op.util;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.User;

public class TestUserSession implements UserSessionSkeleton {

    private User user;

    private Integer idUser;

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

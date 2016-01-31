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

import java.util.Set;

import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.User;

public class UserMock implements User {

    private Integer idUser;

    private String name;

    private String password;

    private Set<Identity> identities;

    /**
     * @return the idUser
     */
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

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the identities
     */
    public Set<Identity> getIdentities() {
	return identities;
    }

    /**
     * @param identities
     *            the identities to set
     */
    public void setIdentities(Set<Identity> identities) {
	this.identities = identities;
    }

}

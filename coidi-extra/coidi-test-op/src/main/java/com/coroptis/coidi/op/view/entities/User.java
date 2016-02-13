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
package com.coroptis.coidi.op.view.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;

import com.coroptis.coidi.op.entities.Identity;
import com.google.common.base.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonVisual
    @Column(name = "id_user")
    private Integer idUser;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<IdentityImpl> identities;

    @Override
    public String toString() {
	return Objects.toStringHelper(User.class).add("idUser", idUser).add("name", name)
		.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#getIdUser()
     */
    public Integer getIdUser() {
	return idUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#setIdUser(java.lang.Integer)
     */
    public void setIdUser(Integer idUser) {
	this.idUser = idUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#getName()
     */
    public String getName() {
	return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#setName(java.lang.String)
     */
    public void setName(String name) {
	this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#getPassword()
     */
    public String getPassword() {
	return password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#setPassword(java.lang.String)
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#getIdentities()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Set<Identity> getIdentities() {
	return (Set) identities;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.User#setIdentities(java.util.Set)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setIdentities(Set<Identity> identities) {
	this.identities = (Set) identities;
    }

}

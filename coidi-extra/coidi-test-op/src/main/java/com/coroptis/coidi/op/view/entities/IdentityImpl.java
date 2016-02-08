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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coroptis.coidi.op.entities.IdentitySreg;
import com.google.common.base.Objects;

@Entity
@Table(name = "identity")
public class IdentityImpl extends AbstractEntity<IdentityImpl> implements IdentitySreg {

    @Id
    @Column(nullable = false, length = 50, name = "id_identity")
    private String idIdentity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private UserImpl user;

    @Column(length = 50)
    private String nickname;

    @Column(length = 250)
    private String email;

    @Column(length = 250)
    private String fullname;

    @Column()
    private Date dob;

    @Column()
    private Gendre gendre;

    @Column(length = 10)
    private String postcode;

    @Column(length = 50)
    private String country;

    @Column(length = 10)
    private String language;

    @Column(length = 50)
    private String timezone;

    @Override
    public String toString() {
	return Objects.toStringHelper(IdentityImpl.class).add("idIdentity", idIdentity).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getIdIdentity()
     */
    @Override
    public String getIdIdentity() {
	return idIdentity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setIdIdentity(java.lang.String)
     */
    @Override
    public void setIdIdentity(String idIdentity) {
	this.idIdentity = idIdentity;
    }

    @Override
    protected Object[] getHashCodeData() {
	return new Object[] { idIdentity };
    }

    @Override
    protected IdentityImpl getThis() {
	return this;
    }

    @Override
    protected boolean dataEquals(IdentityImpl other) {
	if (!areEqual(idIdentity, other.getIdIdentity()))
	    return false;
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getUser()
     */
    public UserImpl getUser() {
	return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setUser(com.coroptis.coidi.op
     * .entities.UserImpl)
     */
    public void setUser(UserImpl user) {
	this.user = user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getNickname()
     */
    @Override
    public String getNickname() {
	return nickname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setNickname(java.lang.String)
     */
    @Override
    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getEmail()
     */
    @Override
    public String getEmail() {
	return email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#setEmail(java.lang.String)
     */
    @Override
    public void setEmail(String email) {
	this.email = email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getFullname()
     */
    @Override
    public String getFullname() {
	return fullname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setFullname(java.lang.String)
     */
    @Override
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getDob()
     */
    @Override
    public Date getDob() {
	return dob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#setDob(java.util.Date)
     */
    @Override
    public void setDob(Date dob) {
	this.dob = dob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getGendre()
     */
    @Override
    public Gendre getGendre() {
	return gendre;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setGendre(com.coroptis.coidi.
     * op.entities.IdentityImpl.Gendre)
     */
    @Override
    public void setGendre(Gendre gendre) {
	this.gendre = gendre;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getPostcode()
     */
    @Override
    public String getPostcode() {
	return postcode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setPostcode(java.lang.String)
     */
    @Override
    public void setPostcode(String postcode) {
	this.postcode = postcode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getCountry()
     */
    @Override
    public String getCountry() {
	return country;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#setCountry(java.lang.String)
     */
    @Override
    public void setCountry(String country) {
	this.country = country;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getLanguage()
     */
    @Override
    public String getLanguage() {
	return language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setLanguage(java.lang.String)
     */
    @Override
    public void setLanguage(String language) {
	this.language = language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.Identity#getTimezone()
     */
    @Override
    public String getTimezone() {
	return timezone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.Identity#setTimezone(java.lang.String)
     */
    @Override
    public void setTimezone(String timezone) {
	this.timezone = timezone;
    }

}

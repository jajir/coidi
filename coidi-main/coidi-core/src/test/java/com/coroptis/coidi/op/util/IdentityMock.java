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

import java.util.Date;

import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.User;

public class IdentityMock implements Identity {

    private String idIdentity;

    private User user;

    private String nickname;

    private String email;

    private String fullname;

    private Date dob;

    private Gendre gendre;

    private String postcode;

    private String country;

    private String language;

    private String timezone;

    /**
     * @return the idIdentity
     */
    public String getIdIdentity() {
	return idIdentity;
    }

    /**
     * @param idIdentity
     *            the idIdentity to set
     */
    public void setIdIdentity(String idIdentity) {
	this.idIdentity = idIdentity;
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
     * @return the nickname
     */
    public String getNickname() {
	return nickname;
    }

    /**
     * @param nickname
     *            the nickname to set
     */
    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
	return fullname;
    }

    /**
     * @param fullname
     *            the fullname to set
     */
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
	return dob;
    }

    /**
     * @param dob
     *            the dob to set
     */
    public void setDob(Date dob) {
	this.dob = dob;
    }

    /**
     * @return the gendre
     */
    public Gendre getGendre() {
	return gendre;
    }

    /**
     * @param gendre
     *            the gendre to set
     */
    public void setGendre(Gendre gendre) {
	this.gendre = gendre;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
	return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode) {
	this.postcode = postcode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
	this.country = country;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
	return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String language) {
	this.language = language;
    }

    /**
     * @return the timezone
     */
    public String getTimezone() {
	return timezone;
    }

    /**
     * @param timezone
     *            the timezone to set
     */
    public void setTimezone(String timezone) {
	this.timezone = timezone;
    }
}

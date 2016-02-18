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
package com.coroptis.coidi.util;

import java.util.Date;

import com.coroptis.coidi.op.entities.IdentitySreg;

public class IdentityMock implements IdentitySreg {

    private String idIdentity;

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
    @Override
    public String getIdIdentity() {
	return idIdentity;
    }

    /**
     * @param idIdentity
     *            the idIdentity to set
     */
    @Override
    public void setIdIdentity(String idIdentity) {
	this.idIdentity = idIdentity;
    }

    /**
     * @return the nickname
     */
    @Override
    public String getNickname() {
	return nickname;
    }

    /**
     * @param nickname
     *            the nickname to set
     */
    @Override
    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    /**
     * @return the email
     */
    @Override
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    @Override
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the fullname
     */
    @Override
    public String getFullname() {
	return fullname;
    }

    /**
     * @param fullname
     *            the fullname to set
     */
    @Override
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    /**
     * @return the dob
     */
    @Override
    public Date getDob() {
	return dob;
    }

    /**
     * @param dob
     *            the dob to set
     */
    @Override
    public void setDob(Date dob) {
	this.dob = dob;
    }

    /**
     * @return the gendre
     */
    @Override
    public Gendre getGendre() {
	return gendre;
    }

    /**
     * @param gendre
     *            the gendre to set
     */
    @Override
    public void setGendre(Gendre gendre) {
	this.gendre = gendre;
    }

    /**
     * @return the postcode
     */
    @Override
    public String getPostcode() {
	return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    @Override
    public void setPostcode(String postcode) {
	this.postcode = postcode;
    }

    /**
     * @return the country
     */
    @Override
    public String getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    @Override
    public void setCountry(String country) {
	this.country = country;
    }

    /**
     * @return the language
     */
    @Override
   public String getLanguage() {
	return language;
    }

    /**
     * @param language
     *            the language to set
     */
    @Override
    public void setLanguage(String language) {
	this.language = language;
    }

    /**
     * @return the timezone
     */
    @Override
    public String getTimezone() {
	return timezone;
    }

    /**
     * @param timezone
     *            the timezone to set
     */
    @Override
    public void setTimezone(String timezone) {
	this.timezone = timezone;
    }
}

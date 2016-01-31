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
package com.coroptis.coidi.op.entities;

import java.util.Date;

public interface Identity {

    public enum Gendre {
	M, F
    }

    /**
     * It get OP Local identifier.
     * 
     * @return the idIdentity
     */
    String getIdIdentity();

    /**
     * It set OP local identifier.
     * 
     * @param idIdentity
     *            the idIdentity to set
     */
    void setIdIdentity(String idIdentity);

    /**
     * @return the user
     */
    User getUser();

    /**
     * @param user
     *            the user to set
     */
    void setUser(User user);

    /**
     * @return the nickname
     */
    String getNickname();

    /**
     * @param nickname
     *            the nickname to set
     */
    void setNickname(String nickname);

    /**
     * @return the email
     */
    String getEmail();

    /**
     * @param email
     *            the email to set
     */
    void setEmail(String email);

    /**
     * @return the fullname
     */
    String getFullname();

    /**
     * @param fullname
     *            the fullname to set
     */
    void setFullname(String fullname);

    /**
     * @return the dob
     */
    Date getDob();

    /**
     * @param dob
     *            the dob to set
     */
    void setDob(Date dob);

    /**
     * @return the gendre
     */
    Gendre getGendre();

    /**
     * @param gendre
     *            the gendre to set
     */
    void setGendre(Gendre gendre);

    /**
     * @return the postcode
     */
    String getPostcode();

    /**
     * @param postcode
     *            the postcode to set
     */
    void setPostcode(String postcode);

    /**
     * @return the country
     */
    String getCountry();

    /**
     * @param country
     *            the country to set
     */
    void setCountry(String country);

    /**
     * @return the language
     */
    String getLanguage();

    /**
     * @param language
     *            the language to set
     */
    void setLanguage(String language);

    /**
     * @return the timezone
     */
    String getTimezone();

    /**
     * @param timezone
     *            the timezone to set
     */
    void setTimezone(String timezone);

}
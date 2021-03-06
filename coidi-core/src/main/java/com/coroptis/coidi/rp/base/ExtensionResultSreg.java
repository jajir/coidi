package com.coroptis.coidi.rp.base;

import java.util.Date;

/**
 * Object will hold result of Simple Registration Extension (sreg) result
 * properties.
 * 
 * @author jirout
 * 
 */
public class ExtensionResultSreg implements ExtensionResult {

    public static final String CODE = "sreg";

    private String nickname;

    private String email;

    private String fullname;

    private Date dob;

    private String gendre;

    private String postcode;

    private String country;

    private String language;

    private String timezone;

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
    public String getGendre() {
	return gendre;
    }

    /**
     * @param gendre
     *            the gendre to set
     */
    public void setGendre(String gendre) {
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

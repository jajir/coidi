package com.coroptis.coidi.core.message;

/**
 * Interface define key that are used in Simple Registration Extension (sreg).
 * 
 * @author jirout
 * 
 */
public interface NsSreg {

	static final String SREG_REQUIRED = "openid.sreg.required";
	static final String SREG_OPTIONAL = "openid.sreg.optional";
	static final String SREG_POLICY_URL = "openid.sreg.policy_url";
	static final String SREG_NICKNAME = "sreg.nickname";
	static final String SREG_EMAIL = "sreg.email";
	static final String SREG_FULLNAME = "sreg.fullname";
	static final String SREG_DOB = "sreg.dob";
	static final String SREG_GENDRE = "sreg.gendre";
	static final String SREG_POSTCODE = "sreg.postcode";
	static final String SREG_COUNTRY = "sreg.country";
	static final String SREG_LANGUAGE = "sreg.language";
	static final String SREG_TIMEZONE = "sreg.timezone";

	static final String DOB_FORMAT = "yyyy-MM-dd";

}

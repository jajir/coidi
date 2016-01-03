package com.coroptis.coidi.rp.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.NsSreg;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.base.ExtensionResultSreg;
import com.coroptis.coidi.rp.services.AuthRespDecoder;

/**
 * Extract Simple registration extension result from authentication response.
 * 
 * @author jirout
 * 
 */
public class AuthRespDecoderSreg implements AuthRespDecoder, NsSreg {

    private final static Logger logger = LoggerFactory.getLogger(AuthRespDecoderSreg.class);

    @Override
    public Boolean decode(AuthenticationResponse authenticationResponse, Association association,
	    AuthenticationResult authenticationResult) {
	boolean touche = false;
	ExtensionResultSreg resultSreg = new ExtensionResultSreg();
	if (authenticationResponse.get(SREG_NICKNAME) != null) {
	    resultSreg.setNickname(authenticationResponse.get(SREG_NICKNAME));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_EMAIL) != null) {
	    resultSreg.setEmail(authenticationResponse.get(SREG_EMAIL));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_FULLNAME) != null) {
	    resultSreg.setFullname(authenticationResponse.get(SREG_FULLNAME));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_DOB) != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(DOB_FORMAT);
	    try {
		resultSreg.setDob(sdf.parse(authenticationResponse.get(SREG_DOB)));
	    } catch (ParseException e) {
		logger.warn("unable to parse date of '" + SREG_DOB + "' in "
			+ authenticationResponse.getMessage());
		return false;
	    }
	    touche = true;
	}
	if (authenticationResponse.get(SREG_GENDRE) != null) {
	    resultSreg.setGendre(authenticationResponse.get(SREG_GENDRE));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_POSTCODE) != null) {
	    resultSreg.setPostcode(authenticationResponse.get(SREG_POSTCODE));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_COUNTRY) != null) {
	    resultSreg.setCountry(authenticationResponse.get(SREG_COUNTRY));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_LANGUAGE) != null) {
	    resultSreg.setLanguage(authenticationResponse.get(SREG_LANGUAGE));
	    touche = true;
	}
	if (authenticationResponse.get(SREG_TIMEZONE) != null) {
	    resultSreg.setTimezone(authenticationResponse.get(SREG_TIMEZONE));
	    touche = true;
	}
	if (touche) {
	    authenticationResult.getExtensions().put(ExtensionResultSreg.CODE, resultSreg);
	}
	return true;
    }
}

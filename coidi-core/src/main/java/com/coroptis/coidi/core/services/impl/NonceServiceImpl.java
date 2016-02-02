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
package com.coroptis.coidi.core.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceService;
import com.google.common.base.Preconditions;

/**
 * 
 * @author jan
 * 
 */
public class NonceServiceImpl implements NonceService {

    private Logger logger = LoggerFactory.getLogger(CryptographyServiceImpl.class);

    private final SecureRandom random;

    @Inject
    private ConvertorService convertorService;

    public NonceServiceImpl() throws NoSuchAlgorithmException {
	random = SecureRandom.getInstance("SHA1PRNG");
    }

    @Override
    public Date extractDate(final String nonce) {
	SimpleDateFormat isoDateFormatter = new SimpleDateFormat(ISO_DATETIME_FORMAT);
	isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	try {
	    return isoDateFormatter.parse(Preconditions.checkNotNull(nonce, "nonce is null"));
	} catch (ParseException e) {
	    return null;
	}
    }

    @Override
    public boolean verifyNonceExpiration(final String nonce, final Integer expirationInMinutes) {
	Date now = new Date();
	Date nonceDateTime = extractDate(nonce);

	if (nonceDateTime == null) {
	    logger.info("Nonce is invalid");
	    return false;
	}

	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	calendar.setTime(nonceDateTime);
	calendar.add(Calendar.MINUTE, expirationInMinutes);
	if (calendar.getTime().before(now)) {
	    logger.debug("Nonce expired. Because now is '" + now + "' and nonce is '"
		    + calendar.getTime() + "'");
	    return false;
	}

	return true;
    }

    @Override
    public String createNonce() {
	SimpleDateFormat isoDateFormatter = new SimpleDateFormat(ISO_DATETIME_FORMAT);
	return isoDateFormatter.format(new Date()) + generateCrumb();
    }

    private String generateCrumb() {
	byte[] b = new byte[10];
	random.nextBytes(b);
	return convertorService.convertToString(b);
    }

	public void setConvertorService(ConvertorService convertorService) {
		this.convertorService = convertorService;
	}
    
}

package com.coroptis.coidi.rp.view.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.view.services.NonceDao;
import com.coroptis.coidi.rp.view.services.NonceService;

/**
 * Helper class for nonce verification.
 * 
 * @author Radek Varbuchta
 */
public class NonceServiceImpl implements NonceService {

	private static Logger logger = Logger.getLogger(NonceServiceImpl.class);
	
	@Inject
	private NonceDao nonceDao;


	public Date extractDate(String nonce) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				NonceDao.ISO_DATETIME_FORMAT);

		try {
			return dateFormat.parse(nonce);
		} catch (ParseException e) {
			return null;
		}
	}

	public boolean verifyNonce(String nonce, int expirationMinutes) {
		if (nonceDao.isExists(nonce)) {
			logger.debug("Not logged in - already used nonce.");
			return false;
		}

		Date now = new Date();
		Date nonceDateTime = extractDate(nonce);
		
		if (nonceDateTime == null) {
			logger.info("Nonce is invalid");
			return false;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nonceDateTime);
		calendar.add(Calendar.MINUTE, expirationMinutes);

		if (now.getTime() > calendar.getTimeInMillis()) {
			logger.debug("Nonce expired.");
			return false;
		}

		nonceDao.storeNonce(nonce);
		return true;
	}

}

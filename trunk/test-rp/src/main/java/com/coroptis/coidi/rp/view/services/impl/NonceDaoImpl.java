package com.coroptis.coidi.rp.view.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.coroptis.coidi.rp.view.services.NonceDao;

/**
 * Simplest implementation based on {@link Set}.
 * 
 * @author jan
 * 
 */
public class NonceDaoImpl implements NonceDao {

	private final static Logger logger = Logger.getLogger(NonceDaoImpl.class);

	private Set<String> nonces = Collections
			.synchronizedSet(new HashSet<String>());

	private static final int NONCE_EXPIRATION_MINUTES = 30;

	public synchronized boolean isExists(String nonce) {
		return nonces.contains(nonce);
	}

	public synchronized void storeNonce(String nonce) {
		nonces.add(nonce);
	}

	private List<String> getNoncesToRemove() {
		List<String> noncesToRemove = new ArrayList<String>();
		Iterator<String> i = nonces.iterator();
		while (i.hasNext()) {
			String nonce = (String) i.next();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					ISO_DATETIME_FORMAT);
			try {
				Date now = new Date();
				Date nonceDateTime = dateFormat.parse(nonce);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(nonceDateTime);
				calendar.add(Calendar.MINUTE, NONCE_EXPIRATION_MINUTES);
				if (now.getTime() > calendar.getTimeInMillis()) {
					noncesToRemove.add(nonce);
				}
			} catch (ParseException e) {
				logger.warn("invalid format of nonce '" + nonce
						+ "', nonce will be removed.");
				noncesToRemove.add(nonce);
			}
		}
		logger.debug("found nonces to remove from memory: "
				+ noncesToRemove.size());
		return noncesToRemove;
	}

	public synchronized void removeOldNonces() {
		logger.debug("number of nonces before cleaning: " + nonces.size());
		List<String> noncesToRemove = getNoncesToRemove();
		Iterator<String> i = noncesToRemove.iterator();
		while (i.hasNext()) {
			nonces.remove((String) i.next());
		}
		logger.debug("number of nonces after cleaning: " + nonces.size());
	}

}

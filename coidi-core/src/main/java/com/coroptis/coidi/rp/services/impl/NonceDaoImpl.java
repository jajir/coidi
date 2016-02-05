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
package com.coroptis.coidi.rp.services.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.rp.services.NonceDao;

/**
 * Simplest implementation based on {@link Set}.
 * 
 * @author jan
 * 
 */
public class NonceDaoImpl implements NonceDao {

    private final static Logger logger = LoggerFactory.getLogger(NonceDaoImpl.class);

    private Set<String> nonces = Collections.synchronizedSet(new HashSet<String>());

    private static final int NONCE_EXPIRATION_MINUTES = 30;

    @Override
    public synchronized boolean isExists(String nonce) {
	return nonces.contains(nonce);
    }

    @Override
    public synchronized void storeNonce(String nonce) {
	nonces.add(nonce);
    }

    private List<String> getNoncesToRemove() {
	List<String> noncesToRemove = new ArrayList<String>();
	Iterator<String> i = nonces.iterator();
	while (i.hasNext()) {
	    String nonce = (String) i.next();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_DATETIME_FORMAT);
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
		logger.warn("invalid format of nonce '" + nonce + "', nonce will be removed.");
		noncesToRemove.add(nonce);
	    }
	}
	logger.debug("found nonces to remove from memory: " + noncesToRemove.size());
	return noncesToRemove;
    }

    @Override
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

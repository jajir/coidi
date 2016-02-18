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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.NonceStorage;

/**
 * Simplest implementation based on {@link Set}.
 * 
 * <p>
 * This implementation will not work in when RP is in clustered deployment.
 * </p>
 * 
 * @author jan
 * 
 */
public class NonceStoreInMemory implements NonceStorage {

	private final static Logger logger = LoggerFactory.getLogger(NonceStoreInMemory.class);

	private final static int DEFAULT_CLEANING_PERIOD_IN_SECONDS = 60 * 5;

	private static final int NONCE_EXPIRATION_MINUTES = 5;

	private Set<String> nonces = new HashSet<String>();

	private final ReentrantLock lock;

	private final Timer timer;

	public NonceStoreInMemory() {
		this(DEFAULT_CLEANING_PERIOD_IN_SECONDS);
	}

	public NonceStoreInMemory(final int cleaningPeriodInSeconds) {
		lock = new ReentrantLock();
		timer = new Timer("Nonce removing task");
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				privateRemoveOldNonces();
			}
		}, cleaningPeriodInSeconds * 1000, cleaningPeriodInSeconds * 1000);
	}

	/**
	 * Manually stop timer.
	 */
	public void stopTimer() {
		timer.cancel();
	}

	@Override
	public boolean isExists(String nonce) {
		try {
			lock.lock();
			return nonces.contains(nonce);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void storeNonce(String nonce) {
		try {
			lock.lock();
			nonces.add(nonce);
		} finally {
			lock.unlock();
		}
	}

	private List<String> getNoncesToRemove() {
		final List<String> noncesToRemove = new ArrayList<String>();
		for (final String nonce : nonces) {
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

	private void privateRemoveOldNonces() {
		logger.trace("number of nonces before cleaning: " + nonces.size());
		try {
			lock.lock();
			for (final String nonce : getNoncesToRemove()) {
				nonces.remove(nonce);
			}
		} finally {
			lock.unlock();
		}
		logger.trace("number of nonces after cleaning: " + nonces.size());
	}

	@Override
	public void removeOldNonces() {
		throw new CoidiException("Removing old nonces is controlled inrnally");
	}

}

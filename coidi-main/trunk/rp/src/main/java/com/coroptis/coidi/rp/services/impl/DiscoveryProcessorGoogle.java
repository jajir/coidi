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

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.google.common.base.Preconditions;

/**
 * This discovery processor allows user to use their imail for login.
 * 
 * @author jan
 * 
 */
public class DiscoveryProcessorGoogle implements DiscoveryProcessor {

	private final static Logger logger = Logger
			.getLogger(DiscoveryProcessorGoogle.class);

	@Inject
	private DiscoverySupport discoverySupport;

	public DiscoveryResult dicovery(String userSuppliedId) {
		Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
		if (discoverySupport.isItEmail(userSuppliedId)) {
			logger.debug("It's gmail id '" + userSuppliedId + "'");
			return discoverySupport.getXrdsDocument(
					"https://www.google.com/accounts/o8/id", userSuppliedId);
		} else {
			return null;
		}
	}

}

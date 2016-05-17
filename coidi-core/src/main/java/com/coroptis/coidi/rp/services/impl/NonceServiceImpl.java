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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.core.services.impl.NonceToolImpl;
import com.coroptis.coidi.rp.services.NonceService;
import com.coroptis.coidi.rp.services.NonceStorage;
import com.google.common.base.Preconditions;

/**
 * Allows to verify nonce string.
 * 
 * @author jirout
 * 
 */
public class NonceServiceImpl implements NonceService {

	private Logger logger = LoggerFactory.getLogger(NonceToolImpl.class);

	private final NonceTool nonceTool;

	private final NonceStorage nonceStorage;

	public NonceServiceImpl(final NonceTool nonceTool, final NonceStorage nonceStorage) {
		this.nonceTool = Preconditions.checkNotNull(nonceTool);
		this.nonceStorage = Preconditions.checkNotNull(nonceStorage);
	}

	@Override
	public boolean isNonceValid(final String nonce, final Integer expirationInMinutes) {
		if (nonceTool.isNonceExpired(nonce, expirationInMinutes)) {
			return false;
		} else {
			if (nonceStorage.isExists(nonce)) {
				logger.info("Nonce '{}' was already used.", nonce);
				return false;
			} else {
				nonceStorage.storeNonce(nonce);
				return true;
			}
		}
	}

}

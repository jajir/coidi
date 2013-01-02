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
package com.coroptis.coidi.op.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.StatelessModeNonce;
import com.coroptis.coidi.op.services.CryptoService;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

public class StatelessModeNonceServiceImpl implements StatelessModeNonceService {

	private final Logger logger;

	@Inject
	private ConvertorService convertorService;

	@Inject
	private CryptoService cryptoService;

	@Inject
	private StatelessModeNonceDao statelessModeNonceDao;

	@Inject
	private SigningService signingService;

	private final AssociationType statelesModeAssociationType;

	public StatelessModeNonceServiceImpl(
			@Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
			final Logger logger) {
		this.logger = logger;
		statelesModeAssociationType = AssociationType.convert(assocTypeStr);
		logger.debug("Association type for stateless mode: "
				+ statelesModeAssociationType);
	}

	@Override
	public StatelessModeNonce createStatelessModeNonce(final String nonce) {
		StatelessModeNonce statelessModeNonce = new StatelessModeNonce();
		statelessModeNonce.setNonce(nonce);
		statelessModeNonce
				.setMacKey(convertorService.convertToString(cryptoService
						.generateAssociationRandom(statelesModeAssociationType)));
		logger.debug("Creating stateless nonce: " + statelessModeNonce);
		statelessModeNonceDao.save(statelessModeNonce);
		return statelessModeNonce;
	}

	@Override
	public Boolean isValidCheckAuthenticationRequest(
			final CheckAuthenticationRequest request) {
		StatelessModeNonce statelessModeNonce = statelessModeNonceDao
				.getByNonce(request.getNonce());
		Preconditions.checkNotNull(statelessModeNonce,
				"nonce '" + request.getNonce()
						+ "' wasn't fourn during sateles authentication");
		String signature = signingService.sign(request,
				statelessModeNonce.getMacKey(), statelesModeAssociationType);
		if (signature.equals(request.getSignature())) {
			return true;
		} else {
			logger.info("Signature from check_authentication message '"
					+ request.getSignature()
					+ "' is not same as computed one '" + signature + "'");
			return false;
		}
	}
}

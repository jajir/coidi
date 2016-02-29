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

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.AssociationProcessor;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.CryptoService;
import com.google.common.base.Preconditions;

@Singleton
public class AssociationProcessorImpl implements AssociationProcessor {

	private final static Logger logger = LoggerFactory.getLogger(AssociationProcessorImpl.class);

	private final BaseAssociationDao baseAssociationDao;

	private final CryptoSessionService cryptoSessionService;

	private final CryptoService cryptoService;

	private final AssociationService associationService;;

	private final ConvertorService convertorService;

	@Inject
	public AssociationProcessorImpl(final BaseAssociationDao baseAssociationDao,
			final CryptoSessionService cryptoSessionService, final CryptoService cryptoService,
			final AssociationService associationService, final ConvertorService convertorService) {
		this.baseAssociationDao = Preconditions.checkNotNull(baseAssociationDao);
		this.cryptoSessionService = Preconditions.checkNotNull(cryptoSessionService);
		this.cryptoService = Preconditions.checkNotNull(cryptoService);
		this.associationService = Preconditions.checkNotNull(associationService);
		this.convertorService = Preconditions.checkNotNull(convertorService);
	}

	@Override
	public AbstractMessage processAssociation(final AssociationRequest request, final SessionType sessionType,
			final AssociationType associationType) {
		Association association = associationService.createAssociation(associationType, sessionType);
		AssociationResponse out = new AssociationResponse();
		if (association.getSessionType().equals(SessionType.NO_ENCRYPTION)) {
			logger.info("No encryption was setup during association request/response.");
			byte macKey[] = cryptoService.generateAssociationRandom(association.getAssociationType());
			association.setMacKey(convertorService.convertToString(macKey));
			out.setMacKey(macKey);
		} else {
			association.setMacKey(convertorService
					.convertToString(cryptoService.generateSessionRandom(association.getSessionType())));
			KeyPair cryptoSession = cryptoSessionService.generateCryptoSession(request);
			out.setEncMacKey(cryptoSessionService.xorSecret(cryptoSession, request.getDhConsumerPublic(),
					convertorService.convertToBytes(association.getMacKey()), sessionType));
			out.setDhServerPublic(cryptoSession.getPublicKey());
		}

		baseAssociationDao.create(association);

		out.setSessionType(association.getSessionType());
		out.setAssociationType(association.getAssociationType());
		out.setAssocHandle(association.getAssocHandle());
		out.setExpiresIn(String.valueOf((association.getExpiredIn().getTime() - System.currentTimeMillis()) / 1000L));
		return out;
	}

}

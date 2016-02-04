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

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.rp.services.AssociationFactory;
import com.coroptis.coidi.rp.services.HttpTransportService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class AssociationFactoryImpl implements AssociationFactory {

	private final static Logger logger = LoggerFactory.getLogger(AssociationFactoryImpl.class);

	@Inject
	private CryptoSessionService cryptoSessionService;

	@Inject
	private ConvertorService convertorService;

	@Inject
	private HttpTransportService httpTransportService;

	@Override
	public Association generateAssociation(final String opEndpoint, final SessionType sessionType,
			final AssociationType associationType) {
		Preconditions.checkNotNull(opEndpoint, "opEndpoint");
		Preconditions.checkNotNull(sessionType, "sessionType");
		Preconditions.checkNotNull(associationType, "associationType");
		logger.debug("Creating association at '" + opEndpoint + "'");

		AssociationRequest associationRequest = new AssociationRequest();
		associationRequest.setAssociationType(associationType);
		associationRequest.setSessionType(sessionType);
		KeyPair keyPair = cryptoSessionService.generateCryptoSession(CryptographyService.DEFAULT_MODULUS,
				CryptographyService.DEFAULT_GENERATOR);
		if (!SessionType.NO_ENCRYPTION.equals(sessionType)) {
			associationRequest.setDhConsumerPublic(keyPair.getPublicKey());
			associationRequest.setDhGen(keyPair.getGenerator());
			associationRequest.setDhModulo(keyPair.getModulus());
		}

		logger.debug("creating association request: " + associationRequest);

		AssociationResponse associationResponse = new AssociationResponse(
				httpTransportService.doPost(opEndpoint, associationRequest.getMap()));
		verifyResponse(associationResponse);
		AssociationBean association = new AssociationBean();
		association.setAssocHandle(associationResponse.getAssocHandle());
		association.setAssociationType(associationResponse.getAssociationType());
		association.setSessionType(associationResponse.getSessionType());
		association.setExpiredIn(getExpireIn(associationResponse.getExpiresIn()));

		if (SessionType.NO_ENCRYPTION.equals(sessionType)) {
			association.setMacKey(convertorService.convertToString(associationResponse.getMacKey()));
		} else {
			byte[] macKey = cryptoSessionService.xorSecret(keyPair, associationResponse.getDhServerPublic(),
					associationResponse.getEncMacKey(), associationResponse.getSessionType());
			association.setMacKey(convertorService.convertToString(macKey));
		}

		return association;
	}

	// MATCH with OP code, should be at same place
	private Date getExpireIn(String seconds) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, Integer.valueOf(Preconditions.checkNotNull(seconds, "seconds")));
		return cal.getTime();
	}

	public void verifyResponse(final AssociationResponse message) {
		if (Strings.isNullOrEmpty(message.getNameSpace())) {
			throw new CoidiException("OpenID namespace was not filled.");
		}
		if (!AbstractMessage.OPENID_NS_20.equals(message.getNameSpace())) {
			throw new CoidiException("OpenID namespace contains invalid value '" + message.getNameSpace() + "'.");
		}
	}

	public void setCryptoSessionService(CryptoSessionService cryptoSessionService) {
		this.cryptoSessionService = cryptoSessionService;
	}

	public void setConvertorService(ConvertorService convertorService) {
		this.convertorService = convertorService;
	}

	public void setHttpTransportService(HttpTransportService httpTransportService) {
		this.httpTransportService = httpTransportService;
	}

}

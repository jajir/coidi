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

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.google.common.base.Preconditions;

public class SigningServiceImpl implements SigningService {

	private Logger logger = LoggerFactory.getLogger(CryptographyServiceImpl.class);

	private final CryptographyService cryptoService;

	private final MessageService messageService;

	private final ConvertorService convertorService;

	@Inject
	public SigningServiceImpl(final CryptographyService cryptoService, final MessageService messageService,
			final ConvertorService convertorService) {
		this.cryptoService = Preconditions.checkNotNull(cryptoService);
		this.messageService = Preconditions.checkNotNull(messageService);
		this.convertorService = Preconditions.checkNotNull(convertorService);
	}

	@Override
	public String sign(final AbstractMessage response, final Association association) {
		String toSign = messageService.extractStringForSign(response, null);
		logger.debug("Message to sign '" + toSign + "'");
		return plainSign(toSign, association.getMacKey(), association.getAssociationType());
	}

	@Override
	public String plainSign(final String toSign, final String macKey, final AssociationType associationType) {
		try {
			byte[] b = cryptoService.generateMac(convertorService.convertToBytes(macKey), toSign.getBytes("UTF-8"),
					associationType);
			return convertorService.convertToString(b);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

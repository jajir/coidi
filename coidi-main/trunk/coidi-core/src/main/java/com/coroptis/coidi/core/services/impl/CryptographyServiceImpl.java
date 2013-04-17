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

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.google.common.base.Preconditions;

public class CryptographyServiceImpl implements CryptographyService {

	@Inject
	private Logger logger;

	@Inject
	private CryptoSessionService cryptoSessionService;

	@Override
	public byte[] generateMac(final byte[] key, final byte[] text,
			final AssociationType associationType) {
		try {
			SecretKey sk = new SecretKeySpec(key,
					associationType.getAlgorithmName());
			Mac m = Mac.getInstance(sk.getAlgorithm());
			m.init(sk);
			return m.doFinal(text);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] encryptSecret(final KeyPair keyPair,
			final BigInteger dhConsumerPublic, final byte[] macKey,
			final SessionType sessionType) {
		return cryptoSessionService.xorSecret(keyPair, dhConsumerPublic,
				macKey, sessionType);
	}

	@Override
	public byte[] computeDigest(final byte[] text, final SessionType sessionType) {
		Preconditions.checkNotNull(text, "signing text");
		Preconditions.checkNotNull(sessionType, "sessionType");
		if (sessionType.equals(SessionType.no_encription)) {
			throw new CoidiException(
					"Can't sign when session type is 'no-encryption'");
		}
		try {
			MessageDigest d = MessageDigest.getInstance(sessionType
					.getAlgorithmName());
			return d.digest(text);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

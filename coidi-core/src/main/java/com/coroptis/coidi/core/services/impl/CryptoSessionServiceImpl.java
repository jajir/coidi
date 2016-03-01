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
import java.util.Random;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.google.common.base.Preconditions;

public class CryptoSessionServiceImpl implements CryptoSessionService {

	private final CryptographyService cryptographyService;

	private final Random random;

	 
	public CryptoSessionServiceImpl(final CryptographyService cryptographyService) {
		random = new Random();
		this.cryptographyService = Preconditions.checkNotNull(cryptographyService, "");
	}

	@Override
	public BigInteger getSharedSecretKey(final KeyPair keyPair, final BigInteger composite) {
		return composite.modPow(keyPair.getPrivateKey(), keyPair.getModulus());
	}

	@Override
	public byte[] xorSecret(final KeyPair keyPair, final BigInteger otherPublic, final byte[] secret,
			final SessionType sessionType) {
		if (otherPublic == null) {
			throw new IllegalArgumentException("otherPublic cannot be null");
		}

		BigInteger shared = keyPair.getSharedSecret(otherPublic);
		byte[] hashed = cryptographyService.computeDigest(shared.toByteArray(), sessionType);

		if (secret.length != hashed.length) {
			throw new CoidiException(
					"invalid secret byte[] length: secret=" + secret.length + ", hashed=" + hashed.length);
		}

		byte[] result = new byte[secret.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (hashed[i] ^ secret[i]);
		}
		return result;
	}

	@Override
	public KeyPair generateCryptoSession(final AssociationRequest association) {
		return generateCryptoSession(association.getDhModulo(), association.getDhGen());
	}

	@Override
	public KeyPair generateCryptoSession(BigInteger dhModulo, BigInteger dhGen) {
		if (dhModulo == null) {
			dhModulo = DEFAULT_DH_MODULUS;
		}
		if (dhGen == null) {
			dhGen = DEFAULT_DH_GEN;
		}
		int bits = dhModulo.bitLength();
		BigInteger max = dhModulo.subtract(BigInteger.ONE);
		while (true) {
			BigInteger pkey = new BigInteger(bits, random);
			if (pkey.compareTo(max) >= 0) { // too large
				continue;
			} else if (pkey.compareTo(BigInteger.ONE) <= 0) {// too small
				continue;
			}
			return new KeyPair(pkey, dhGen.modPow(pkey, dhModulo));
		}
	}

}

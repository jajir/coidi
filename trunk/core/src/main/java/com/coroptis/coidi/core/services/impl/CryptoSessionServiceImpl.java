package com.coroptis.coidi.core.services.impl;

import java.math.BigInteger;
import java.util.Random;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.SessionType;

public class CryptoSessionServiceImpl implements CryptoSessionService {

	@Inject
	private CryptographyService cryptographyService;

	private final Random random;

	public CryptoSessionServiceImpl() {
		random = new Random();
	}

	@Override
	public BigInteger getSharedSecretKey(KeyPair keyPair, BigInteger composite) {
		return composite.modPow(keyPair.getPrivateKey(), keyPair.getModulus());
	}

	@Override
	public byte[] xorSecret(KeyPair keyPair, BigInteger otherPublic,
			byte[] secret, final SessionType sessionType) {
		if (otherPublic == null) {
			throw new IllegalArgumentException("otherPublic cannot be null");
		}

		BigInteger shared = keyPair.getSharedSecret(otherPublic);
		byte[] hashed = cryptographyService.computeDigest(shared.toByteArray(),
				sessionType);

		if (secret.length != hashed.length) {
			throw new CoidiException("invalid secret byte[] length: secret="
					+ secret.length + ", hashed=" + hashed.length);
		}

		byte[] result = new byte[secret.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (hashed[i] ^ secret[i]);
		}
		return result;
	}

	@Override
	public KeyPair generateCryptoSession(AssociationRequest association) {
		return generateCryptoSession(association.getDhModulo(),
				association.getDhGen());
	}

	@Override
	public KeyPair generateCryptoSession(BigInteger dhModulo, BigInteger dhGen) {
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

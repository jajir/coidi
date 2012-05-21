package com.coroptis.coidi.core.services.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.util.KeyPair;

public class CryptoSessionServiceImpl implements CryptoSessionService {

	@Inject
	private Logger logger;

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
			byte[] secret) {
		if (otherPublic == null) {
			throw new IllegalArgumentException("otherPublic cannot be null");
		}

		BigInteger shared = keyPair.getSharedSecret(otherPublic);
		byte[] hashed = sha1(shared.toByteArray());

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
		// TODO could it work with not default p and q?
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

	// TODO move ciphering
	private byte[] sha1(byte[] text) {
		try {
			MessageDigest d = MessageDigest.getInstance("SHA-1");
			return d.digest(text);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

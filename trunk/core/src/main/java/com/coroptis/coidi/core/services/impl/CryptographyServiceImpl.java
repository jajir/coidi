package com.coroptis.coidi.core.services.impl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
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

public class CryptographyServiceImpl implements CryptographyService {

	@Inject
	private Logger logger;

	@Inject
	private CryptoSessionService cryptoSessionService;

	@Override
	public byte[] hmacSha1(byte[] key, byte[] text) {
		try {
			SecretKey sk = new SecretKeySpec(key, "HMACSHA1");
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
	public byte[] encryptSecret(KeyPair keyPair, BigInteger dhConsumerPublic,
			byte[] macKey) {
		return cryptoSessionService
				.xorSecret(keyPair, dhConsumerPublic, macKey);
	}

}

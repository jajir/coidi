package com.coroptis.coidi.core.util;

import java.math.BigInteger;

import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;

/**
 * Holder for public key, private key, modulus and generator. This number
 * together allows to compute shared secret between OpenID provider and relaying
 * party.
 * <p>
 * {@link KeyPair} is created by {@link CryptoSessionService}.
 * </p>
 * 
 * @author jan
 * 
 */
public class KeyPair {

	private final BigInteger privateKey;

	private final BigInteger publicKey;

	private final BigInteger modulus;

	private final BigInteger generator;

	public KeyPair(final BigInteger privateKey, final BigInteger publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.modulus = CryptographyService.DEFAULT_MODULUS;
		this.generator = CryptographyService.DEFAULT_GENERATOR;
	}

	public KeyPair(final BigInteger privateKey, final BigInteger publicKey,
			final BigInteger modulus, final BigInteger generator) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.modulus = modulus;
		this.generator = generator;
	}

	public BigInteger getSharedSecret(BigInteger composite) {
		return composite.modPow(privateKey, modulus);
	}

	/**
	 * Returns the private key.
	 * 
	 * @return the private key.
	 */
	public BigInteger getPrivateKey() {
		return privateKey;
	}

	/**
	 * Returns the public key.
	 * 
	 * @return the public key.
	 */
	public BigInteger getPublicKey() {
		return publicKey;
	}

	/**
	 * @return the modulus
	 */
	public BigInteger getModulus() {
		return modulus;
	}

	/**
	 * @return the generator
	 */
	public BigInteger getGenerator() {
		return generator;
	}

}

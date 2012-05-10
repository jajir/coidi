package com.coroptis.coidi.op.view.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

import com.coroptis.coidi.op.view.services.AssociationService;

public class CryptoSession {

	private BigInteger modulus;
	private BigInteger generator;
	private BigInteger privateKey;
	private BigInteger publicKey;

	private final static Logger log = Logger.getLogger(CryptoSession.class);

	private static SecureRandom random;

	static {
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("No secure random available!");
		}
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
	 * Creates a DiffieHellman instance.
	 * 
	 * @param mod
	 *            the modulus to use. If null, use {@link #DEFAULT_MODULUS}.
	 * @param gen
	 *            the generator to use. If null, use {@link #DEFAULT_GENERATOR}.
	 */
	public CryptoSession(BigInteger mod, BigInteger gen) {
		modulus = (mod != null ? mod : AssociationService.DEFAULT_MODULUS);
		generator = (gen != null ? gen : AssociationService.DEFAULT_GENERATOR);

		int bits = modulus.bitLength();
		BigInteger max = modulus.subtract(BigInteger.ONE);
		while (true) {
			BigInteger pkey = new BigInteger(bits, random);
			if (pkey.compareTo(max) >= 0) { // too large
				continue;
			} else if (pkey.compareTo(BigInteger.ONE) <= 0) {// too small
				continue;
			}
			privateKey = pkey;
			publicKey = generator.modPow(privateKey, modulus);
			break;
		}
	}

	/**
	 * Returns the shared secret.
	 * 
	 * @param composite
	 *            the composite number (public key) with which this instance
	 *            shares a secret.
	 * @return the shared secret.
	 */
	private BigInteger getSharedSecret(BigInteger composite) {
		return composite.modPow(privateKey, modulus);
	}

	/**
	 * Returns the shared secret SHA-1 hashed and XOR 'encrypted'.
	 * 
	 * @param otherPublic
	 *            the other party's public modulus; cannot be null.
	 * @param secret
	 *            the key to XOR encrypt with.
	 * @return the encrypted secret.
	 * @throws IllegalArgumentException
	 *             if <code>otherPublic</code> is null.
	 * @throws RuntimeException
	 *             if length of <code>secret</code> is incorrect.
	 */
	byte[] xorSecret(BigInteger otherPublic, byte[] secret)
			throws NoSuchAlgorithmException {
		if (otherPublic == null) {
			throw new IllegalArgumentException("otherPublic cannot be null");
		}

		BigInteger shared = getSharedSecret(otherPublic);
		byte[] hashed = Crypto.sha1(shared.toByteArray());

		if (secret.length != hashed.length) {
			log.warn("invalid secret byte[] length: secret=" + secret.length
					+ ", hashed=" + hashed.length);
			throw new RuntimeException("nyi");
		}

		byte[] result = new byte[secret.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (hashed[i] ^ secret[i]);
		}
		return result;
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

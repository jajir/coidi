//
// (C) Copyright 2007 VeriSign, Inc.  All Rights Reserved.
//
// VeriSign, Inc. shall have no responsibility, financial or
// otherwise, for any consequences arising out of the use of
// this material. The program material is provided on an "AS IS"
// BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied.
//
// Distributed under an Apache License
// http://www.apache.org/licenses/LICENSE-2.0
//

package com.coroptis.coidi.rp.view.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;

/**
 * Implements the underlying Diffie-Hellman cryptography.
 */
public class DiffieHellman {

	Logger logger = Logger.getLogger(DiffieHellman.class);

	private BigInteger modulus;
	private BigInteger generator;
	private BigInteger privateKey;
	private BigInteger publicKey;

	/**
	 * The default modulus defined in the specification.
	 */
	public static final BigInteger DEFAULT_MODULUS = new BigInteger(
			"1551728981814736974712322577637155399157248019669"
					+ "154044797077953140576293785419175806512274236981"
					+ "889937278161526466314385615958256881888899512721"
					+ "588426754199503412587065565498035801048705376814"
					+ "767265132557470407658574792912915723345106432450"
					+ "947150072296210941943497839259847603755949858482"
					+ "53359305585439638443");

	/**
	 * The default generator defined in the specification.
	 */
	public static final BigInteger DEFAULT_GENERATOR = BigInteger.valueOf(2);

	/**
	 * Returns a Diffie-Hellman instance using default modulus and generator.
	 * Note that each call to this method will return an instance with random
	 * private key.
	 * 
	 * @return a DiffieHellman instance using modulus
	 *         ${#DiffieHellman.DEFAULT_MODULUS}, and generator
	 *         ${#DiffieHellman.DEFAULT_GENERATOR}.
	 */
	public static DiffieHellman getDefault() {
		BigInteger p = DiffieHellman.DEFAULT_MODULUS;
		BigInteger g = DiffieHellman.DEFAULT_GENERATOR;
		return new DiffieHellman(p, g);
	}

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
	public DiffieHellman(BigInteger mod, BigInteger gen) {
		modulus = (mod != null ? mod : DiffieHellman.DEFAULT_MODULUS);
		generator = (gen != null ? gen : DiffieHellman.DEFAULT_GENERATOR);

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
	public byte[] xorSecret(BigInteger otherPublic, byte[] secret)
			throws NoSuchAlgorithmException {
		if (otherPublic == null) {
			throw new IllegalArgumentException("otherPublic cannot be null");
		}

		BigInteger shared = getSharedSecret(otherPublic);
		byte[] hashed = Convert.sha1(shared.toByteArray());

		if (secret.length != hashed.length) {
			logger.error("invalid secret byte[] length: secret=" + secret.length
					+ ", hashed=" + hashed.length);
			throw new CoidiException("invalid secret byte[] length: secret=" + secret.length
					+ ", hashed=" + hashed.length);
		}

		byte[] result = new byte[secret.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (hashed[i] ^ secret[i]);
		}
		return result;
	}

	/**
	 * Returns the shared secret.
	 * 
	 * @param composite
	 *            the composite number (public key) with which this instance
	 *            shares a secret.
	 * @return the shared secret.
	 */
	public BigInteger getSharedSecret(BigInteger composite) {
		return composite.modPow(privateKey, modulus);
	}
}
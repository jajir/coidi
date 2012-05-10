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

package com.coroptis.coidi.op.view.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.coroptis.coidi.CoidiException;

/**
 * Implements the cryptography needed for OpenID.
 */
public class Crypto {

	/**
	 * Creates an instance of the crypto library.
	 */
	public Crypto() {
	}

	private CryptoSession dh;

	/**
	 * Digests a message using SHA-1.
	 * 
	 * @param text
	 *            the bytes to digest.
	 * @return the digested bytes.
	 * @throws NoSuchAlgorithmException
	 *             if SHA-1 is not available.
	 */
	 static byte[] sha1(byte[] text) throws NoSuchAlgorithmException {
		MessageDigest d = MessageDigest.getInstance("SHA-1");
		return d.digest(text);
	}

	/**
	 * Sets the Diffie-Hellman key values.
	 * 
	 * @param dh
	 *            the Diffie-Hellman value.
	 */
	public void setDiffieHellman(CryptoSession dh) {
		this.dh = dh;
	}

	/**
	 * Returns the Diffie-Hellman public key set by
	 * {@link #setDiffieHellman(BigInteger, BigInteger)}.
	 * 
	 * @return the Diffie-Hellman public key.
	 * @throws IllegalArgumentException
	 *             if this crypto instance has not yet been initialized.
	 */
	public BigInteger getPublicKey() {
		if (dh == null) {
			throw new IllegalArgumentException("DH not yet initialized");
		}
		return dh.getPublicKey();
	}

	/**
	 * Encrypts a secret using Diffie-Hellman.
	 * 
	 * @param consumerPublic
	 *            the public key used to encrypt.
	 * @param secret
	 *            the value to encrypt.
	 * @return the encrypted secret value.
	 */
	public byte[] encryptSecret(BigInteger consumerPublic, byte[] secret)
			throws CoidiException {
		if (dh == null) {
			throw new IllegalArgumentException("No DH implementation set");
		}
		byte[] xoredSecret = null;
		try {
			xoredSecret = dh.xorSecret(consumerPublic, secret);
			return xoredSecret;
		} catch (NoSuchAlgorithmException e) {
			throw new CoidiException(e.getMessage(), e);
		}
	}

	/**
	 * Converts a string to bytes.
	 * 
	 * @param s
	 *            the string to convert.
	 * @return the base 64 decoded bytes.
	 */
	public static byte[] convertToBytes(String s) {
		return Base64.decodeBase64(s.getBytes());
	}

	/**
	 * Converts bytes to string.
	 * 
	 * @param b
	 *            the bytes to encode.
	 * @return the base 64 encoded string.
	 */
	public static String convertToString(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * Converts a big integer to string.
	 * 
	 * @param b
	 *            the big integer to convert.
	 * @return the base 64 encoded string.
	 */
	public static String convertToString(BigInteger b) {
		return convertToString(b.toByteArray());
	}

	/**
	 * Converts a base64-encoded string to big int.
	 * 
	 * @param s
	 *            the string to convert, by way of base64 decoding.
	 * @return the converted big int.
	 */
	public static BigInteger convertToBigIntegerFromString(String s) {
		return new BigInteger(convertToBytes(s));
	}
}

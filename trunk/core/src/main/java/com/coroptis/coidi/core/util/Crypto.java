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

package com.coroptis.coidi.core.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Implements the cryptography needed for OpenID.
 */
public class Crypto {

	/**
	 * Creates an instance of the crypto library.
	 */
	private Crypto() {
	}

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
	 * Signs a message using HMACSHA1.
	 * 
	 * @param key
	 *            the key to sign with.
	 * @param text
	 *            the bytes to sign.
	 * @return the signed bytes.
	 * @throws InvalidKeyException
	 *             if <code>key</code> is not a good HMAC key.
	 * @throws NoSuchAlgorithmException
	 *             if HMACSHA1 is not available.
	 */
	public static byte[] hmacSha1(byte[] key, byte[] text)
			throws InvalidKeyException, NoSuchAlgorithmException {
		SecretKey sk = new SecretKeySpec(key, "HMACSHA1");
		Mac m = Mac.getInstance(sk.getAlgorithm());
		m.init(sk);
		return m.doFinal(text);
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

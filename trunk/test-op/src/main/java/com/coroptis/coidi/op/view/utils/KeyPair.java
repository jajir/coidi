package com.coroptis.coidi.op.view.utils;

import java.math.BigInteger;

/**
 * Holder for public and private key pairs.
 * 
 * @author jan
 * 
 */
public class KeyPair {

	private final BigInteger privateKey;

	private final BigInteger publicKey;

	public KeyPair(final BigInteger privateKey, final BigInteger publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
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

}

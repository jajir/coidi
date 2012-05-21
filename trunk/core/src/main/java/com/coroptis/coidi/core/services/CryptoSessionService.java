package com.coroptis.coidi.core.services;

import java.math.BigInteger;

import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.util.KeyPair;

public interface CryptoSessionService {
	
	
	/**
	 * 
	 * @param keyPair
	 * @param composite
	 * @return
	 */
	BigInteger getSharedSecretKey(KeyPair keyPair, BigInteger composite);

	byte[] xorSecret(KeyPair keyPair, BigInteger otherPublic, byte[] secret);
	
	KeyPair generateCryptoSession(AssociationRequest association);
	
	KeyPair generateCryptoSession(BigInteger dhModulo, BigInteger dhGen);
	
}

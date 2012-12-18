/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.core.services;

import java.math.BigInteger;

import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface CryptoSessionService {

	/**
	 * 
	 * @param keyPair
	 * @param composite
	 * @return
	 */
	BigInteger getSharedSecretKey(KeyPair keyPair, BigInteger composite);

	byte[] xorSecret(KeyPair keyPair, BigInteger otherPublic, byte[] secret,
			SessionType sessionType);

	KeyPair generateCryptoSession(AssociationRequest association);

	KeyPair generateCryptoSession(BigInteger dhModulo, BigInteger dhGen);

}

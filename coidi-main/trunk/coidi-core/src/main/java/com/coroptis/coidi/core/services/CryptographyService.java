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

import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface CryptographyService {

    public static final BigInteger DEFAULT_MODULUS = new BigInteger(
	    "1551728981814736974712322577637155399157248019669"
		    + "154044797077953140576293785419175806512274236981"
		    + "889937278161526466314385615958256881888899512721"
		    + "588426754199503412587065565498035801048705376814"
		    + "767265132557470407658574792912915723345106432450"
		    + "947150072296210941943497839259847603755949858482" + "53359305585439638443");

    public static final BigInteger DEFAULT_GENERATOR = BigInteger.valueOf(2);

    /**
     * Generating "Message Authentication Code" (MAC) for given text.
     * 
     * @param key
     *            required private key
     * @param text
     *            required signed message
     * @param associationType
     *            required association type defining signing algorithm
     * @return signature
     */
    byte[] generateMac(byte[] key, byte[] text, final AssociationType associationType);

    byte[] encryptSecret(KeyPair keyPair, BigInteger dhConsumerPublic, byte[] macKey,
	    SessionType sessionType);

    byte[] computeDigest(final byte[] text, final SessionType sessionType);

}

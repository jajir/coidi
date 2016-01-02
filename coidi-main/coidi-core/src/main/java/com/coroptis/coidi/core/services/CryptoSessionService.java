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

    final static BigInteger DEFAULT_DH_MODULUS = new BigInteger("1551728981814736974712322577637155"
	    + "3991572480196691540447970779531405" + "7629378541917580651227423698188993"
	    + "7278161526466314385615958256881888" + "8995127215884267541995034125870655"
	    + "6549803580104870537681476726513255" + "7470407658574792912915723345106432"
	    + "4509471500722962109419434978392598" + "4760375594985848253359305585439638443");

    public static final BigInteger DEFAULT_DH_GEN = BigInteger.valueOf(2);

    /**
     * 
     * @param keyPair
     *            pair of private and public key
     * @param composite
     *            composite parameter
     * @return computed shared secret key
     */
    BigInteger getSharedSecretKey(KeyPair keyPair, BigInteger composite);

    byte[] xorSecret(KeyPair keyPair, BigInteger otherPublic, byte[] secret,
	    SessionType sessionType);

    /**
     * Generates key pair (private key and public key) from association request.
     * 
     * @param authenticationRequest
     *            required authentication request.
     * @return generated pair of keys
     */
    KeyPair generateCryptoSession(AssociationRequest authenticationRequest);

    /**
     * Based on inputs generate key pair.
     * 
     * @param dhModulo
     *            optional DH modulus, if it's <code>null</code> than default
     *            value {@link #DEFAULT_DH_MODULUS} is used.
     * @param dhGen
     *            optional DH generator, if it's <code>null</code> than default
     *            value {@link #DEFAULT_DH_GEN} is used.
     * @return computed {@link KeyPair}
     */
    KeyPair generateCryptoSession(BigInteger dhModulo, BigInteger dhGen);

}

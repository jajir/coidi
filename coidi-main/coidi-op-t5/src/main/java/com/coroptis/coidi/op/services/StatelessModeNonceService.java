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
package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.entities.Nonce;

public interface StatelessModeNonceService {

    /**
     * Return verified nonce object. When it's not possible to verify given
     * nonce parameter or nonce parameter is <code>null</code> than
     * <code>null</code> is returned.
     * 
     * @param nonce
     *            optional nonce
     * @return verifier nonce object or <code>null</code>
     */
    Nonce getVerifiedNonce(String nonce);

    /**
     * Valid if request is valid. For verifications is used nonce. Verify if in
     * request is valid association and nonce belongs to this association.
     * 
     * @param nonce
     * @param request
     * @return
     */
    Boolean isAssociationValid(Nonce nonce, CheckAuthenticationRequest request);
}

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
package com.coroptis.coidi.op.dao;

import com.coroptis.coidi.op.entities.Nonce;

/**
 * It's for StatelessMode.
 * 
 * @author jirout
 * 
 */
public interface BaseNonceDao {

    void save(Nonce statelessModeNonce);

    Nonce getByNonce(String noce);

    /**
     * Remove nonce from store.
     * 
     * @param nonce
     *            required nonce object
     */
    void delete(Nonce nonce);

    /**
     * Just create new empty object instance. Instance is not persisted.
     * 
     * @return new {@link Nonce} object instance
     */
    Nonce createNewInstance();

}

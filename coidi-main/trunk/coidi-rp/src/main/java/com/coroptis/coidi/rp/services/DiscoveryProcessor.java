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
package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * Implementation try to discover OP Endpoint URL and other information
 * necessary for further authentication.
 * <p>
 * Implementations of this interface are called in chain of command. When
 * {@link #dicovery(String)} methods return <code>null</code> next
 * implementation in chain is called.
 * </p>
 * 
 * @author jan
 * 
 */
public interface DiscoveryProcessor {

    /**
     * Try perform discovery with selected technology.
     * 
     * @param userSuppliedId
     *            required user supplied ID
     * @return discovery result object when process is successful otherwise
     *         return <code>null</code>
     */
    DiscoveryResult dicovery(String userSuppliedId);

}

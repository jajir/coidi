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

import com.coroptis.coidi.op.entities.Identity;

/**
 * Provide basic operation with persisted identities.
 * 
 * @author jirout
 * 
 */
public interface BaseIdentityDao {

    /**
     * Get identity by it's name. Name is unique within OP.
     * 
     * @param idIdentity
     *            required idenitity's name (id)
     * @return {@link Identity} object if there is any otherwise
     *         <code>null</code>
     */
    Identity getIdentityId(String idIdentity);

}

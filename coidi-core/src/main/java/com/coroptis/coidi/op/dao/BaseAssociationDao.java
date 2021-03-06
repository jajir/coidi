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

import com.coroptis.coidi.op.entities.Association;

/**
 * Allows to persist associations.
 * 
 * @author jirout
 * 
 */
public interface BaseAssociationDao {

    /**
     * Persist new association. During persisting should also persist all
     * including object like nonces.
     * 
     * @param association
     *            required association
     */
    void create(Association association);

    Association getByAssocHandle(String assoc_handle);

    /**
     * Create new empty {@link Association} instance. Returned object is not
     * persisted.
     * 
     * @return new {@link Association} object
     */
    Association createNewInstance();

    /**
     * Delete permanently from persistent store.
     * 
     * @param association
     *            required association
     */
    void delete(Association association);

}

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

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface AssociationService {

    /**
     * Permanently delete association from persistent store.
     * 
     * @param associationHandle
     *            required association handle
     */
    void delete(String associationHandle);

    /**
     * Find out if association is valid or not. It try to load it from database
     * and that verify that is not stale.
     * 
     * @param assoc_handle
     * @return <code>true</code> if association exists and is valid otherwise
     *         return <code>false</code>
     */
    boolean isValid(String assoc_handle);

    /**
     * Create shared association.
     * 
     * @param associationType
     *            required association type
     * @param sessionType
     *            required session type
     * @return
     */
    Association createAssociation(AssociationType associationType, SessionType sessionType);

    /**
     * Create association with default association type and with
     * <code>null</code> session type. It's state-less association.
     * 
     * @return
     */
    Association createStateLessAssociation();

}

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

import java.util.Date;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;

/**
 * Helps to manage associations.
 * 
 * @author jirout
 * 
 */
public interface AssociationTool {

    final static String DEFAULT_TIME_TO_LIVE_IN_SECONDS = "op.nonce.timeToLiveInSeconds";

    final static String DEFAULT_ASSOCITION_TYPE = "op.stateless.mode.association.type";

    /**
     * Get date up to which is association handle considered as valid. It's
     * based on co
     * 
     * @return
     */
    Date getTimeToLive();

    /**
     * Get default association type. It's used in state-less mode, when
     * association is created on OP.
     * 
     * @return default association type value
     */
    AssociationType getDefaultAssociationType();

    /**
     * Get information if it's shared or private association. When session type
     * of association is <code>null</code> then it's private association.
     * Private association could be part of state-less association.
     * 
     * @param association
     *            required association
     * @return <code>true</code> if it's private association otherwise return
     *         <code>false</code>
     */
    Boolean isPrivateAssociation(Association association);

}

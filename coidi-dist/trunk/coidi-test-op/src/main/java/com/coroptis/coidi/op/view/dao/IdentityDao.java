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
package com.coroptis.coidi.op.view.dao;

import java.util.List;

import com.coroptis.coidi.op.entities.Identity;

public interface IdentityDao {

    /**
     * Get total number of identities.
     * 
     * @return number of persisted identities
     */
    Integer getCount();

    /**
     * Get sublist of identities ordered by it's id. All parameters are zero
     * based.
     * 
     * @param startIndex
     *            optional start index, when it's <code>null</code> it will get
     *            records from 0.
     * @param endIndex
     *            optional end index of returned records, if it's
     *            <code>null</code> it will return record until last one.
     * @return
     */
    List<Identity> getChunk(Integer startIndex, Integer endIndex);

}

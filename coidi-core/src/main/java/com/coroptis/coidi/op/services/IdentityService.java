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

import com.coroptis.coidi.op.entities.Identity;

/**
 * Identity managing service.
 *
 * @author jan
 *
 *
 */
public interface IdentityService {

    /**
     * Get {@link Identity} by it's OP local identifier.
     *
     * @param opLocalIdentifier
     *            required OP local identifier, could be any string that could
     *            contains identity. Method check if parameter is in correct
     *            format.
     * @return {@link Identity} object if there is any otherwise
     *         <code>null</code>
     */
    Identity getByOpLocalIdentifier(String opLocalIdentifier);

}

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

import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;

/**
 * 
 * @author jan
 * 
 */
public interface IdentityService {

    /**
     * Get identity by it's name. Name is unique within OP.
     * 
     * @param idIdentity
     *            required idenitity's name (id)
     * @return {@link IdentityImpl} object if there is any otherwise
     *         <code>null</code>
     */
    Identity getIdentityByName(String idIdentity);

    /**
     * Get identity by it's id. Id is composed 'op.idenity.prefix'/name.
     * 
     * @param id
     *            required identity id
     * @return {@link IdentityImpl} object if there is any otherwise
     *         <code>null</code>
     */
    @Deprecated
    Identity getById(String id);

    /**
     * Verify that claimed identity is logged into given user session.
     * 
     * @param userSession
     *            optional user session
     * @param claimedIdentity
     *            required claimed identity
     * @return return <code>true</code> when user session is not
     *         <code>null</code> and contains user that owns claimed identity
     *         otherwise return <code>false</code>
     */
    Boolean isIdentityLogged(UserSessionSkeleton userSession, Identity claimedIdentity);

    /**
     * Verify that identity belongs to user.
     * 
     * @param idUser
     *            required user's id
     * @param identityName
     *            required identity id, usually URL
     * @return <code>true</code> if identity belongs to user otherwise return
     *         <code>false</code>
     */
    Boolean isUsersIdentity(final Integer idUser, final String identityName);
}

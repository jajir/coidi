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
package com.coroptis.coidi.op.base;

import com.coroptis.coidi.core.message.AuthenticationRequest;

/**
 * OpenID provider application should implement it. Object should be stored in
 * HTTP session.
 * 
 * @author jirout
 * 
 */
public interface UserSessionSkeleton {

    /**
     * Return return <code>true</code> when user is logged into OP otherwise
     * return <code>false</code>.
     * 
     * @return return boolean
     */
    boolean isLogged();

    /**
     * Logger user id.
     * 
     * TODO remove it, not all users have unique id.
     * 
     * @return the idUser
     */
    Integer getIdUser();

    /**
     * When authentication request is processed and user is not logged than
     * original authentication request have to be stored. After user logged in
     * original request should be processed again.
     */
    void setAuthenticationRequest(AuthenticationRequest authenticationRequest);

}
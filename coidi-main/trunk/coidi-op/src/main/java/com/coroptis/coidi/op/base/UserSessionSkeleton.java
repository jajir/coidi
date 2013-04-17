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

    boolean isLogged();

    /**
     * @return the idUser
     */
    Integer getIdUser();

    void setAuthenticationRequest(AuthenticationRequest authenticationRequest);

}
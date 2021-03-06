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

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;

/**
 * Allows to process one specific openID message that are coming to technical
 * OpenID end point.
 * 
 * @author jan
 * 
 */
public interface OpenIdDispatcher {

    /**
     * OpenID name space.
     */
    final static String OPENID_NS = AbstractMessage.OPENID + AbstractMessage.OPENID_NS;

    /**
     * OpenID mode.
     */
    final static String OPENID_MODE = AbstractMessage.OPENID + AbstractMessage.MODE;

    /**
     * OpenID session type.
     */
    final static String OPENID_SESSION_TYPE = AbstractMessage.OPENID + AssociationRequest.SESSION_TYPE;

    /**
     * OpenID association type.
     */
    final static String OPENID_ASSOC_TYPE = AbstractMessage.OPENID + AssociationRequest.ASSOCIATION_TYPE;
    
    /**
     * OpenID DH consumer public.
     */
    final static String OPENID_DH_CONSUMER_PUBLIC = AbstractMessage.OPENID + AssociationRequest.DH_CONSUMER_PUBLIC;

    /**
     * Process message. If return value is <code>null</code> then next openID
     * request dispatcher is used. It will be used in chain of command patter.
     * 
     * @param requestParams
     *            required contains key value OpenID request parameters.
     * @param userSession
     *            optional, contains data about logged user from users HTPP
     *            session.
     * @return {@link AbstractMessage} object containing positive some OpenID
     *         response. When it's <code>null</code> next dispatcher will be
     *         called to process request.
     */
    AbstractMessage process(Map<String, String> requestParams, HttpSession userSession);

}

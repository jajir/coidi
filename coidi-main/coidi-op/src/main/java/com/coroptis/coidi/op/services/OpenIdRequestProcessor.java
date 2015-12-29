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

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.base.UserSessionSkeleton;

/**
 * This interface should be called by OP application. Interface optionally call
 * OpenID protocol version 1.0 and 1.1 if there are supported.
 * 
 * @author jirout
 * 
 */
public interface OpenIdRequestProcessor {

    final static String CONF_OPENID_VERSION_11_ENABLED = "op.openid.version11.enabled";

    AbstractMessage process(Map<String, String> requestParams, UserSessionSkeleton userSession);

}

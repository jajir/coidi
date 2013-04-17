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

import java.util.Set;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity;

/**
 * Interface define chain of commands that process incoming authentication
 * request and helps produce authentication respose.
 * <p>
 * Each parts of chain of command change response, only terminator return non
 * null value.
 * </p>
 * 
 * @author jirout
 * 
 */
public interface AuthenticationProcessor {

    /**
     * 
     * @param authenticationRequest
     * @param response
     * @param identity
     * @param fieldsToSign
     *            required parameter where will be added all fields from message
     *            that should be signed
     * @return
     */
    AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign);
}

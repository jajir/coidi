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
package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.CoidiException;

/**
 * This runtime exception is throws from discovery process and authentication
 * request processing. Exception contains message that should be presented to
 * end user.
 * 
 * @author jan
 * 
 */
public class AuthenticationProcessException extends CoidiException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public AuthenticationProcessException(String message, Throwable throwable) {
	super(message, throwable);
    }

    public AuthenticationProcessException(String message) {
	super(message);
    }

}

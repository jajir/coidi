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

import com.coroptis.coidi.core.message.ErrorResponse;

/**
 * Helps to generate error messages during authentication process.
 * 
 * @author jirout
 * 
 */
public interface NegativeResponseGenerator {

    /**
     * It's key under which is stored some of following simple messages
     * describing problem. Most of problem could be corrected by OP by asking
     * end user.
     */
    static final String APPLICATION_ERROR_KEY = "appErr";

    /**
     * User should choose identity that would like to use.
     */
    static final String APPLICATION_ERROR_SELECT_IDENTITY = "selectIdentity";

    /**
     * End user should login at OP site.
     */
    static final String APPLICATION_ERROR_PLEASE_LOGIN = "pleaseLogin";

    /**
     * Generated error with given message.
     * 
     * @param message
     *            required error message
     * @return created instance of {@link ErrorResponse} class
     */
    ErrorResponse simpleError(String message);

    /**
     * Generated error with given message.
     * 
     * @param nameSpace
     *            required name space
     * @param message
     *            required error message
     * @return created instance of {@link ErrorResponse} class
     */
    ErrorResponse simpleError(String message, String nameSpace);

    /**
     * This generate error that tells application that there should correct
     * something.
     * 
     * @param message
     *            required message
     * @param errorKey
     *            required error key
     * @return created error response
     */
    ErrorResponse applicationError(String message, String errorKey);

    /**
     * This generate error that tells application that there should correct
     * something.
     * 
     * @param nameSpace
     *            required name space
     * @param message
     *            required message
     * @param errorKey
     *            required error key
     * @return error response object
     */
    ErrorResponse applicationError(String message, String errorKey, String nameSpace);

}

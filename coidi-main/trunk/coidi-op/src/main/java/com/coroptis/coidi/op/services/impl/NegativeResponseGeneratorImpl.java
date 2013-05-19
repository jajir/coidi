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
package com.coroptis.coidi.op.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;

/**
 * Simple {@link NegativeResponseGenerator} implementation.
 * 
 * @author jirout
 * 
 */
public class NegativeResponseGeneratorImpl implements NegativeResponseGenerator {

    @Inject
    private Logger logger;

    @Inject
    @Symbol("op.err.contact")
    private String errorContact;

    @Override
    public ErrorResponse simpleError(final String message) {
	logger.warn(message);
	ErrorResponse errorResponse = new ErrorResponse(false);
	errorResponse.setContact(errorContact);
	errorResponse.setError(message);
	return errorResponse;
    }

    @Override
    public ErrorResponse simpleError(final String message, final String nameSpace) {
	logger.warn(message);
	ErrorResponse errorResponse = new ErrorResponse(false);
	errorResponse.setContact(errorContact);
	errorResponse.setError(message);
	errorResponse.setNameSpace(nameSpace);
	return errorResponse;
    }

    @Override
    public ErrorResponse applicationError(final String message, final String errorKey) {
	logger.warn(message);
	ErrorResponse errorResponse = new ErrorResponse(false);
	errorResponse.setContact(errorContact);
	errorResponse.put(APPLICATION_ERROR_KEY, errorKey);
	errorResponse.setError(message);
	return errorResponse;
    }

    @Override
    public ErrorResponse applicationError(final String message, final String errorKey,
	    final String nameSpace) {
	logger.warn(message);
	ErrorResponse errorResponse = new ErrorResponse(false);
	errorResponse.setContact(errorContact);
	errorResponse.put(APPLICATION_ERROR_KEY, errorKey);
	errorResponse.setError(message);
	errorResponse.setNameSpace(nameSpace);
	return errorResponse;
    }

}

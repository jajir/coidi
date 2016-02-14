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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.google.common.base.Strings;

/**
 * Simple {@link NegativeResponseGenerator} implementation.
 * 
 * @author jirout
 * 
 */
public class NegativeResponseGeneratorImpl implements NegativeResponseGenerator {

    private final static String MISSING_1 = "Required parameter '";
    private final static String MISSING_2 = "' is missing.";

    private final static Logger logger = LoggerFactory
	    .getLogger(NegativeResponseGeneratorImpl.class);

    private final String errorContact;

    @Inject
    public NegativeResponseGeneratorImpl(final OpConfigurationService configurationService) {
	this.errorContact = configurationService.getErrorContact();
	if (Strings.isNullOrEmpty(errorContact)) {
	    logger.warn("Error contact was not set.");
	}
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

    @Override
    public ErrorResponse missingParameter(final String parameter) {
	return buildError(MISSING_1, parameter, MISSING_2);
    }

    @Override
    public ErrorResponse buildError(final String... strings) {
	final StringBuilder sb = new StringBuilder(100);
	for (final String str : strings) {
	    sb.append(str);
	}
	final String message = sb.toString();
	logger.warn(message);
	ErrorResponse errorResponse = new ErrorResponse(false);
	errorResponse.setContact(errorContact);
	errorResponse.setError(message);
	return errorResponse;
    }

    @Override
    public ErrorResponse buildErrorWithNs(final String nameSpace, final String... strings) {
	final ErrorResponse err = buildError(strings);
	err.setNameSpace(nameSpace);
	return err;
    }

}

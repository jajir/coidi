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

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.google.common.base.Preconditions;

/**
 * Implementation of {@link IdentityNamesConvertor}.
 * 
 * @author jirout
 * 
 */
public class IdentityNamesConvertorImpl implements IdentityNamesConvertor {

    private final static String PLACEHOLDER = "%identity%";

    private final String identityPattern;

    public IdentityNamesConvertorImpl(
	    @Inject @Symbol("op.identity.pattern") final String identityPattern) {
	this.identityPattern = identityPattern;
    }

    @Override
    public String getOpLocalIdentifier(final String opIdentifier) {
	Preconditions.checkNotNull(opIdentifier, "opIdentifier is null");
	final String start = getFirstPart();
	final String end = identityPattern.substring(start.length() + PLACEHOLDER.length());
	final String part = opIdentifier.substring(start.length());
	return part.substring(0, part.length() - end.length());
    }

    @Override
    public String getOpIdentifier(final String opLocalIdentifier) {
	Preconditions.checkNotNull(opLocalIdentifier, "opLocalIdentifier is null");
	final String start = getFirstPart();
	final String end = identityPattern.substring(start.length() + PLACEHOLDER.length());
	return start + opLocalIdentifier + end;
    }

    private String getFirstPart() {
	final int pos = identityPattern.indexOf(PLACEHOLDER);
	if (pos < 0) {
	    throw new CoidiException(
		    "Invalid configration op.identity.pattern, there is no placeholder '"
			    + PLACEHOLDER + "' ");
	} else {
	    return identityPattern.substring(0, pos);
	}
    }

    public Boolean isOpLocalIdentifier(final String someIdentifier) {
	Preconditions.checkNotNull(someIdentifier, "someIdentifier is null");
	final String start = getFirstPart();
	final String end = identityPattern.substring(start.length() + PLACEHOLDER.length());
	return !someIdentifier.startsWith(start) || !someIdentifier.endsWith(end);
    }
}

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

import org.apache.log4j.Logger;
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

    private final Logger logger = Logger.getLogger(IdentityNamesConvertor.class);

    private final static String PLACEHOLDER = "%identity%";

    private final String identityPattern;

    public IdentityNamesConvertorImpl( // NO_UCD
	    @Inject @Symbol("op.identity.pattern") final String identityPattern) {
	this.identityPattern = identityPattern;
	Preconditions.checkNotNull(this.identityPattern);
    }

    @Override
    public String convertToIdentityId(final String opIdentifier) {
	if (logger.isDebugEnabled()) {
	    logger.debug("converting '" + opIdentifier + "' to op local identifier with pattern '"
		    + identityPattern + "'");
	}
	Preconditions.checkNotNull(opIdentifier, "opIdentifier is null");
	final String start = getFirstPart();
	final String end = identityPattern.substring(start.length() + PLACEHOLDER.length());
	if (start.length() > opIdentifier.length()) {
	    throw new CoidiException("Given OP Identifier '" + opIdentifier
		    + "' is not valid and can't be converted to identity id with pattern '"
		    + identityPattern + "'.");
	}
	final String part = opIdentifier.substring(start.length());
	if (part.endsWith(end)) {
	    return part.substring(0, part.length() - end.length());
	} else {
	    throw new CoidiException("Given OP Identifier '" + opIdentifier
		    + "' is not valid and can't be converted to identity id with pattern '"
		    + identityPattern + "'.");
	}
    }

    @Override
    public String convertToOpLocalIdentifier(final String opLocalIdentifier) {
	if (logger.isDebugEnabled()) {
	    logger.debug("converting '" + opLocalIdentifier + "' to op identifier with pattern '"
		    + identityPattern + "'");
	}
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

    @Override
    public Boolean isOpLocalIdentifier(final String opIdentifier) {
	if (opIdentifier == null) {
	    return false;
	}
	final String start = getFirstPart();
	final String end = identityPattern.substring(start.length() + PLACEHOLDER.length());
	if (start.length() > opIdentifier.length()) {
	    return false;
	}
	final String part = opIdentifier.substring(start.length());
	return (part.endsWith(end));
    }
}

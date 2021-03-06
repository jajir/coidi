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
package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.view.utils.XrdsStreamResponse;
import com.coroptis.coidi.rp.services.XrdsService;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
public class Identity { // NO_UCD

    @Inject
    private Logger logger;

    @Inject
    private IdentityService identityService;

    @Inject
    private XrdsService xrdsService;

    @Inject
    private Request request;

    private String identityName;

    @Property
    private com.coroptis.coidi.op.entities.Identity identity;

    @InjectPage
    private Error404 error404;

    Object onActivate(final String identityName) {
	this.identityName = identityName;
	logger.debug("Requested identity id '" + identityName + "'");
	if (identityName == null) {
	    throw new CoidiException("user name '" + identityName + "' is null");
	}
	identity = identityService.getIdentityByName(identityName);
	if (identity == null) {
	    logger.info("identity '" + identityName + "' is null");
	    return error404;
	}
	if (request.getHeader("Accept") != null
		&& request.getHeader("Accept").toString().indexOf("application/xrds+xml") >= 0) {
	    return new XrdsStreamResponse(xrdsService.getDocument(identityName));
	}
	return null;
    }

    String onPassivate() {
	return identityName;
    }

}

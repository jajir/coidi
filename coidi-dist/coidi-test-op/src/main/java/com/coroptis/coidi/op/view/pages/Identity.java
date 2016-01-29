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
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.util.PrintableXrd;
import com.coroptis.coidi.op.util.PrintableXrdService;
import com.coroptis.coidi.op.util.PrintableXrds;
import com.coroptis.coidi.op.view.utils.XrdsStreamResponse;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
public class Identity { // NO_UCD

    @Inject
    @Symbol("op.server")
    private String opServer;

    @Inject
    private Logger logger;

    @Inject
    private IdentityService identityService;

    @Inject
    private Request request;

    @Inject
    private Response response;

    @Inject
    private IdentityNamesConvertor identityNamesConvertor;

    private String opLocalIdentifier;

    @Property
    private com.coroptis.coidi.op.entities.Identity identity;

    @InjectPage
    private Error404 error404;

    Object onActivate(final String identityId) {
	this.opLocalIdentifier = identityId;
	logger.debug("Requested identity id '" + identityId + "'");
	if (identityId == null) {
	    throw new CoidiException("user name '" + identityId + "' is null");
	}
	identity = identityService.getByIdentityId(identityId);
	if (identity == null) {
	    logger.info("identity '" + identityId + "' is null");
	    return error404;
	}
	response.setHeader("X-XRDS-Location",
		identityNamesConvertor.convertToOpLocalIdentifier(identityId) + "?xrds=get");
	response.setHeader("Vary:", "Accept");
	if (request.getParameter("xrds") != null) {
	    logger.debug("Based on request parameter xrds return XRDS document");
	    return generateXrds();
	}
	if (request.getHeader("Accept") != null
		&& request.getHeader("Accept").toString().indexOf("application/xrds+xml") >= 0) {
	    logger.debug("Based on request header 'Accept: application/xrds+xml' return XRDS document");
	    return generateXrds();
	}
	return null;
    }

    String onPassivate() {
	return opLocalIdentifier;
    }

    public String getOpLocalIdentifier() {
	return identityNamesConvertor.convertToOpLocalIdentifier(opLocalIdentifier);
    }

    private XrdsStreamResponse generateXrds() {
	PrintableXrds xrds = new PrintableXrds();
	xrds.getXrds().add(new PrintableXrd());
	xrds.getXrds().get(0).setVersion("2.0");

	PrintableXrdService service = new PrintableXrdService();
	service.setPriority(1);
	service.getTypes().add(OpenIdNs.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0);
	service.getTypes().add(OpenIdNs.TYPE_SREG_1_1);
	service.setUri(getOpEndpoint());
	service.setLocalID(identityNamesConvertor.convertToOpLocalIdentifier(opLocalIdentifier));
	xrds.getXrds().get(0).getServices().add(service);

	service = new PrintableXrdService();
	service.setPriority(3);
	service.getTypes().add(OpenIdNs.TYPE_OPENID_2_0);
	service.getTypes().add(OpenIdNs.TYPE_SREG_1_1);
	service.setUri(getOpEndpoint());

	xrds.getXrds().get(0).getServices().add(service);
	logger.debug(xrds.print(new StringBuffer(), "").toString());
	return new XrdsStreamResponse(xrds.print(new StringBuffer(), "").toString());
    }

    public String getOpEndpoint() {
	return opServer + "openid";
    }
}

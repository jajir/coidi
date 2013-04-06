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

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.XrdsService;
import com.google.common.base.Preconditions;

public class XrdsServiceImpl implements XrdsService {

    @Inject
    private Logger logger;

    @Inject
    private ConfigurationService configurationService;

    @Inject
    private IdentityNamesConvertor identityNamesConvertor;

    @Override
    public String getDocument(final String user) throws CoidiException {
	Preconditions.checkNotNull(user, "user name");
	Map<String, String> conf = configurationService.loadDefaultConfiguration("op");
	StringBuilder buff = new StringBuilder(1000);
	buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	buff.append("<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\" xmlns:openid=\"http://openid.net/xmlns/1.0\">\n");
	buff.append("  <XRD>\n");
	buff.append("    <Service priority=\"100\" xmlns=\"xri://$xrd*($v*2.0)\">\n");
	buff.append("      <Type>http://specs.openid.net/auth/2.0/signon</Type>\n");
	buff.append("      <Type>" + OpenIdNs.TYPE_SREG_1_1 + "</Type>\n");
	buff.append("      <URI>");
	buff.append(conf.get("server"));
	buff.append("openid</URI>\n");
	buff.append("      <LocalID>");
	buff.append(identityNamesConvertor.getOpIdentifier(user));
	buff.append("</LocalID>\n");
	buff.append("    </Service>\n");
	buff.append("    <Service priority=\"10\" xmlns=\"xri://$xrd*($v*2.0)\">\n");
	buff.append("      <Type>http://specs.openid.net/auth/2.0/server</Type>\n");
	buff.append("      <Type>" + OpenIdNs.TYPE_SREG_1_1 + "</Type>\n");
	buff.append("      <URI>");
	buff.append(conf.get("server"));
	buff.append("openid</URI>\n");
	buff.append("    </Service>\n");
	buff.append("  </XRD>\n");
	buff.append("</xrds:XRDS>");
	logger.debug("returning XRDS document: " + buff.toString());
	return buff.toString();
    }

}

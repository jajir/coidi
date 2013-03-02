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
package com.coroptis.coidi.rp.services.impl;

import java.io.IOException;
import java.net.ConnectException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XmlProcessing;
import com.coroptis.coidi.rp.services.XrdsService;
import com.google.common.base.Preconditions;

/**
 * Yadis.
 * 
 * @author jan
 * 
 */
public class DiscoveryProcessorYadis implements DiscoveryProcessor {

    private final static Logger logger = Logger.getLogger(DiscoveryProcessorYadis.class);

    @Inject
    private HttpService httpService;

    @Inject
    private XrdsService xrdsService;

    @Inject
    private XmlProcessing xmlProcessing;

    @Inject
    private DiscoverySupport discoverySupport;

    private DiscoveryResult doHead(String userSuppliedId) throws ClientProtocolException,
	    IOException {
	logger.debug("trying HEAD at '" + userSuppliedId + "'");
	DefaultHttpClient httpClient = httpService.getHttpClient();
	HttpHead httpHead = new HttpHead(userSuppliedId);
	httpHead.setHeader("Accept", "application/xrds+xml");
	HttpResponse response = httpClient.execute(httpHead);
	Header header = response.getFirstHeader("X-XRDS-Location");
	if (header == null) {
	    return doGet(userSuppliedId);
	} else {
	    return discoverySupport.getXrdsDocument(header.getValue(), userSuppliedId);
	}
    }

    private boolean isContentType(Header headerContentType) {
	if (headerContentType == null) {
	    return false;
	} else {
	    return headerContentType.getValue().indexOf("application/xrds+xml") >= 0;
	}
    }

    private DiscoveryResult doGet(String userSuppliedId) throws ClientProtocolException,
	    IOException {
	logger.debug("trying GET at '" + userSuppliedId + "'");
	HttpGet httpget = new HttpGet(userSuppliedId);
	httpget.setHeader("Accept", "application/xrds+xml");
	HttpResponse response = httpService.getHttpClient().execute(httpget);
	Header headerXrdsLocation = response.getFirstHeader("X-XRDS-Location");
	if (headerXrdsLocation == null) {
	    Header headerContentType = response.getFirstHeader("Content-Type");
	    String body = EntityUtils.toString(response.getEntity());
	    if (isContentType(headerContentType)) {
		// it's XRDS document
		DiscoveryResult out = xrdsService.extractDiscoveryResult(body);
		out.setClaimedId(userSuppliedId);
		return out;
	    } else {
		// try if it's HTML with meta
		String meta = xmlProcessing.getMetaContent(body, "X-XRDS-Location");
		if (meta == null) {
		    throw new AuthenticationProcessException("Unable to find XRDS document.");
		} else {
		    return discoverySupport.getXrdsDocument(meta, userSuppliedId);
		}
	    }
	} else {
	    return discoverySupport.getXrdsDocument(headerXrdsLocation.getValue(), userSuppliedId);
	}
    }

    public DiscoveryResult dicovery(String userSuppliedId) {
	Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
	userSuppliedId = userSuppliedId.trim();
	try {
	    return doHead(userSuppliedId);
	} catch (IllegalArgumentException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException("Invalid format of you identificator");
	} catch (ClientProtocolException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException(
		    "There is problem to get XRDS document, check your identificator");
	} catch (ConnectException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException(
		    "Unable to connect to OpenID provider, check your identificator");
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException(
		    "There is problem to get XRDS document, check your identificator");
	}
    }

}

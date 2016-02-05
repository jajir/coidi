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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;

public class DiscoverySupportImpl implements DiscoverySupport {

    private final static Logger logger = LoggerFactory.getLogger(DiscoverySupportImpl.class);

    private final Pattern patternEmail;

    private final Pattern patternXri;

    private final Pattern patternDomainSlash;

    private final UrlValidator urlValidator;

    @Inject
    private HttpService httpService;

    @Inject
    private XrdsService xrdsService;

    public DiscoverySupportImpl() {
	patternEmail = Pattern.compile(EMAIL_PATTERN);
	patternXri = Pattern.compile(XRI_PATTERN);
	patternDomainSlash = Pattern.compile(DOMAIN_SLASH_PATTERN);
	String[] schemes = { "http", "https" };
	urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_2_SLASHES
		+ UrlValidator.ALLOW_LOCAL_URLS + UrlValidator.NO_FRAGMENTS);
    }

    @Override
    public DiscoveryResult getXrdsDocument(final String xrdsDocumentUrl, final String claimedId)
	    throws AuthenticationProcessException {
	try {
	    if (logger.isDebugEnabled()) {
		logger.debug("trying to get at: " + xrdsDocumentUrl);
	    }
	    HttpGet httpget = new HttpGet(xrdsDocumentUrl);
	    httpget.setHeader("Accept", "application/xrds+xml");
	    HttpResponse resp = httpService.getHttpClient().execute(httpget);
	    DiscoveryResult out = xrdsService.extractDiscoveryResult(EntityUtils.toString(resp
		    .getEntity()));
	    out.setClaimedId(claimedId);
	    return out;
	} catch (IllegalArgumentException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException("Invalid format of you identificator");
	} catch (ClientProtocolException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException(
		    "There is problem to get XRDS document, check your identificator");
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	    throw new AuthenticationProcessException(
		    "There is problem to get XRDS document, check your identificator");
	}
    }

    @Override
    public Boolean isItEmail(final String email) {
	if (email == null) {
	    return false;
	} else {
	    Matcher matcher = patternEmail.matcher(email);
	    return matcher.matches();
	}
    }

    @Override
    public Boolean isItUrl(final String url) {
	if (url == null) {
	    return false;
	} else {
	    return urlValidator.isValid(url);
	}
    }

    @Override
    public Boolean isXri(final String identifier) {
	if (identifier == null) {
	    return false;
	} else {
	    Matcher matcher = patternXri.matcher(identifier);
	    return matcher.matches();
	}
    }

    @Override
    public String normalize(String userSuppliedId) {
	if (isXri(userSuppliedId)) {
	    if (userSuppliedId.startsWith("xri://")) {
		userSuppliedId = userSuppliedId.substring("xri://".length());
	    }
	} else {
	    if (!userSuppliedId.startsWith("http") && !userSuppliedId.startsWith("https")) {
		userSuppliedId = "http://" + userSuppliedId;
	    }
	    if (userSuppliedId.indexOf("#") > 0) {
		userSuppliedId = userSuppliedId.substring(0, userSuppliedId.indexOf("#"));
	    }
	    if (patternDomainSlash.matcher(userSuppliedId).matches()) {
		userSuppliedId = userSuppliedId + "/";
	    }
	}
	return userSuppliedId;
    }

}

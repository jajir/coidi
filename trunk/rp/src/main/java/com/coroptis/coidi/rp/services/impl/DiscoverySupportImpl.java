package com.coroptis.coidi.rp.services.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;

public class DiscoverySupportImpl implements DiscoverySupport {

	private static final String EMAIL_PATTERN = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";

	private static final String XRI_PATTERN = "^([=@+$!\\(]|xri://).*";

	private static final String DOMAIN_SLASH_PATTERN = "^(http://|https://)[^/]*";

	private final Pattern patternEmail;

	private final Pattern patternXri;

	private final Pattern patternDomainSlash;

	@Inject
	private Logger logger;

	@Inject
	private HttpService httpService;

	@Inject
	private XrdsService xrdsService;

	public DiscoverySupportImpl() {
		patternEmail = Pattern.compile(EMAIL_PATTERN);
		patternXri = Pattern.compile(XRI_PATTERN);
		patternDomainSlash = Pattern.compile(DOMAIN_SLASH_PATTERN);
	}

	@Override
	public DiscoveryResult getXrdsDocument(final String xrdsDocumentUrl)
			throws AuthenticationProcessException {
		try {
			HttpGet httpget = new HttpGet(xrdsDocumentUrl);
			httpget.setHeader("Accept", "application/xrds+xml");
			HttpResponse resp = httpService.getHttpClient().execute(httpget);
			return xrdsService.extractDiscoveryResult(EntityUtils.toString(resp
					.getEntity()));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new AuthenticationProcessException(
					"Invalid format of you identificator");
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
			if (!userSuppliedId.startsWith("http")
					&& !userSuppliedId.startsWith("http")) {
				userSuppliedId = "http://" + userSuppliedId;
			}
			if (userSuppliedId.indexOf("#") > 0) {
				userSuppliedId = userSuppliedId.substring(0,
						userSuppliedId.indexOf("#"));
			}
			if (patternDomainSlash.matcher(userSuppliedId).matches()) {
				userSuppliedId = userSuppliedId + "/";
			}
		}
		return userSuppliedId;
	}

}

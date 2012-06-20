package com.coroptis.coidi.rp.services.impl;

import java.io.IOException;

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

	@Inject
	private Logger logger;

	@Inject
	private HttpService httpService;

	@Inject
	private XrdsService xrdsService;

	@Override
	public DiscoveryResult getXrdsDocument(String xrdsDocumentUrl)
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

}

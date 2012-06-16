package com.coroptis.coidi.rp.services.impl;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;

/**
 * Yadis.
 * 
 * @author jan
 * 
 */
public class DiscoveryProcessorYadis implements DiscoveryProcessor {

	private final static Logger logger = Logger
			.getLogger(DiscoveryProcessorYadis.class);

	@Inject
	private HttpService httpService;

	@Inject
	private XrdsService xrdsService;

	
	public DiscoveryResult dicovery(String userSuppliedId) {
		try {
			DefaultHttpClient httpClient = httpService.getHttpClient();
			HttpHead httpHead = new HttpHead(userSuppliedId);
			httpHead.setHeader("Accept", "application/xrds+xml");
			HttpResponse response = httpClient.execute(httpHead);
			Header header = response.getFirstHeader("X-XRDS-Location");
			if (header == null) {
				// try GET
				HttpGet httpget = new HttpGet(userSuppliedId);
				httpget.setHeader("Accept", "application/xrds+xml");
				HttpResponse resp = httpClient.execute(httpget);
				String body = EntityUtils.toString(resp.getEntity());
				logger.debug(body);
				DiscoveryResult endpoint = xrdsService.extractDiscoveryResult(body);
				logger.info("yadis resolving ... at '" + endpoint.getEndPoint() + "'");
				return endpoint;
			} else {
				HttpGet httpget = new HttpGet(header.getValue());
				httpget.setHeader("Accept", "application/xrds+xml");
				HttpResponse resp = httpClient.execute(httpget);
				String body = EntityUtils.toString(resp.getEntity());
				DiscoveryResult endpoint = xrdsService.extractDiscoveryResult(body);
				logger.info("yadis resolving ... at '" + endpoint.getEndPoint() + "'");
				return endpoint;
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.view.services.HttpService;
import com.coroptis.coidi.rp.view.services.YadisService;

public class YadisServiceImpl implements YadisService {

	@Inject
	private Logger logger;

	@Inject
	private HttpService httpService;

	public void readXrdsDocument(String url) {
		try {
			HttpClient httpClient = httpService.getHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Accept", "application/xrds+xml");
			HttpResponse response;
			response = httpClient.execute(httpget);

			HttpEntity entity = response.getEntity();
			String string = EntityUtils.toString(entity);
			System.out.println(string);
			// extract "openid2.provider"
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}

	}

}

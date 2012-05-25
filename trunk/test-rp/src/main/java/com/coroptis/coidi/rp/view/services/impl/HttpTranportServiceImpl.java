package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.util.EntityUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.view.services.HttpService;
import com.coroptis.coidi.rp.view.services.HttpTransportService;
import com.google.common.base.Preconditions;

public class HttpTranportServiceImpl implements HttpTransportService {

	@Inject
	private Logger logger;

	@Inject
	private HttpService httpService;

	@Override
	public Map<String, String> doPost(final String url,
			final Map<String, String> map) {
		Preconditions.checkNotNull(url, "URL");
		try {
			HttpPost post = new HttpPost(url);
			post.getParams().setBooleanParameter(
					AllClientPNames.HANDLE_REDIRECTS, false);
			post.setEntity(new UrlEncodedFormEntity(httpService.toList(map),
					"UTF-8"));
			HttpResponse httpResponse = httpService.getHttpClient().execute(
					post);
			String resp = EntityUtils.toString(httpResponse.getEntity());
			logger.debug("response: " + resp);
			return httpService.convertToMap(resp);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}
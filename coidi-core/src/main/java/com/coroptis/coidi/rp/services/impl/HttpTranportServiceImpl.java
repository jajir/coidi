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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.HttpTransportService;
import com.google.common.base.Preconditions;

public class HttpTranportServiceImpl implements HttpTransportService {

	private final static Logger logger = LoggerFactory.getLogger(HttpTranportServiceImpl.class);

	private final HttpService httpService;

	 
	public HttpTranportServiceImpl(final HttpService httpService) {
		this.httpService = Preconditions.checkNotNull(httpService);
	}

	@Override
	public Map<String, String> doPost(final String url, final Map<String, String> map) {
		Preconditions.checkNotNull(url, "URL");
		try {
			HttpPost post = new HttpPost(url);
			post.getParams().setBooleanParameter(AllClientPNames.HANDLE_REDIRECTS, false);
			post.setEntity(new UrlEncodedFormEntity(httpService.toList(map), "UTF-8"));
			HttpResponse httpResponse = httpService.getHttpClient().execute(post);
			String resp = EntityUtils.toString(httpResponse.getEntity());
			if (HttpServletResponse.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
				logger.debug("HTTP status code '{}' response: {}", httpResponse.getStatusLine().getStatusCode(), resp);
				return httpService.convertToMap(resp);
			} else {
				logger.error("HTTP status code '{}' invalid response: {}", httpResponse.getStatusLine().getStatusCode(),
						resp);
				throw new CoidiException(
						"Unable to process response code '" + httpResponse.getStatusLine().getStatusCode() + "'");
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

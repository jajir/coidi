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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.YadisService;
import com.google.common.base.Preconditions;

public class YadisServiceImpl implements YadisService {

	private final static Logger logger = LoggerFactory.getLogger(YadisServiceImpl.class);

	private final HttpService httpService;

	 
	public YadisServiceImpl(final HttpService httpService) {
		this.httpService = Preconditions.checkNotNull(httpService);
	}

	@Override
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

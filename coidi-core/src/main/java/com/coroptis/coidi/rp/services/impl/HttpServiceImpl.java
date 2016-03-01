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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class HttpServiceImpl implements HttpService {

    private final DefaultHttpClient httpClient;

     
    public HttpServiceImpl(final RpConfigurationService configurationService) {
	httpClient = new DefaultHttpClient();
	String proxyServer = configurationService.getProxyServer();
	if (!Strings.isNullOrEmpty(proxyServer)) {
	    Integer proxyPort = configurationService.getProxyPort();
	    if (proxyPort != null && proxyPort > 0) {
		HttpHost proxy = new HttpHost(proxyServer, proxyPort);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		String userName = configurationService.getProxyUsername();
		String password = configurationService.getProxyPassword();
		if (userName != null) {
		    httpClient.getCredentialsProvider().setCredentials(
			    new AuthScope(proxyServer, proxyPort),
			    new UsernamePasswordCredentials(userName, password));
		}
	    }
	}
	Preconditions.checkNotNull(httpClient, "httpClient");
    }

    @Override
    public DefaultHttpClient getHttpClient() {
	return httpClient;
    }

    @Override
    public Map<String, String> convertToMap(String body) {
	Map<String, String> out = new HashMap<String, String>();
	for (String line : body.split("\n")) {
	    int pos = line.indexOf(":");
	    if (pos > 0) {
		out.put(line.substring(0, pos), line.substring(pos + 1));
	    }
	}
	return out;
    }

    @Override
    public List<NameValuePair> toList(Map<String, String> parameters) {
	List<NameValuePair> list = new ArrayList<NameValuePair>(parameters.size());
	for (Entry<String, String> entry : parameters.entrySet()) {
	    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	}
	return list;
    }
}

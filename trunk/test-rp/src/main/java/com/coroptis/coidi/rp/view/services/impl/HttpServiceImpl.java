package com.coroptis.coidi.rp.view.services.impl;

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
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.rp.view.services.HttpService;
import com.google.common.base.Preconditions;

public class HttpServiceImpl implements HttpService {

	private final DefaultHttpClient httpClient;

	public HttpServiceImpl(
			final @Inject ConfigurationService configurationService) {
		httpClient = new DefaultHttpClient();
		String proxyServer = configurationService.getProperty("proxy.server");
		if (proxyServer != null) {
			Integer proxyPort = configurationService
					.getPropertyInt("proxy.port");
			if (proxyPort == null) {
				proxyPort = -1;
			}
			HttpHost proxy = new HttpHost(proxyServer, proxyPort);
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);

			String userName = configurationService
					.getProperty("proxy.userName");
			String password = configurationService
					.getProperty("proxy.password");
			if (userName != null) {
				httpClient.getCredentialsProvider().setCredentials(
						new AuthScope(proxyServer, proxyPort),
						new UsernamePasswordCredentials(userName, password));
			}
		}
		Preconditions.checkNotNull(httpClient, "httpClient");
	}

	@Override
	public DefaultHttpClient getHttpClient() throws CoidiException {
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
		List<NameValuePair> list = new ArrayList<NameValuePair>(
				parameters.size());
		for (Entry<String, String> entry : parameters.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}
}

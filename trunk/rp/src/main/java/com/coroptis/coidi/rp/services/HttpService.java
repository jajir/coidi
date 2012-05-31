package com.coroptis.coidi.rp.services;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.coroptis.coidi.CoidiException;

public interface HttpService {

	/**
	 * Provide access to configured {@link HttpClient}.
	 * 
	 * @return {@link HttpClient} object
	 * @throws CoidiException
	 */
	DefaultHttpClient getHttpClient() throws CoidiException;

	List<NameValuePair> toList(Map<String, String> parameters);

	Map<String, String> convertToMap(String body);
}

package com.coroptis.coidi.rp.view.services;

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

}

package com.coroptis.coidi.rp.view.pages;

import org.apache.http.client.HttpClient;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.view.services.HttpService;

public class Index {

	@Property
	private String userSuppliedId;

	@Inject
	private HttpService httpService;

	void onSuccess() {
		HttpClient httpClient = httpService.getHttpClient();

		System.out.println(httpClient.getParams());
	}

}

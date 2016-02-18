package com.coroptis.coidi.junit.rp.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.impl.HttpTranportServiceImpl;

public class HttpTranportServiceTest {

	private HttpTranportServiceImpl httpTranportService;

	private HttpService httpService;

	private HttpClient httpClient;

	private HttpResponse response;

	private List<NameValuePair> pairList;

	private Map<String, String> map;

	private StatusLine statusLike_200;

	private StatusLine statusLike_403;

	@Test
	public void test_passOk() throws Exception {
		Map<String, String> result = new HashMap<String, String>();

		EasyMock.expect(httpService.toList(map)).andReturn(pairList);
		EasyMock.expect(httpService.getHttpClient()).andReturn(httpClient);
		EasyMock.expect(httpClient.execute((HttpPost) EasyMock.anyObject())).andReturn(response);
		EasyMock.expect(httpService.convertToMap("Ahoj")).andReturn(result);
		EasyMock.replay(httpService, httpClient);
		Map<String, String> ret = httpTranportService.doPost("http://www.google.com/", map);

		assertNotNull(ret);
		assertSame(result, ret);
		EasyMock.verify(httpService, httpClient);
	}

	@Test
	public void test_response_403() throws Exception {
		response.setStatusLine(statusLike_403);
		EasyMock.expect(httpService.toList(map)).andReturn(pairList);
		EasyMock.expect(httpService.getHttpClient()).andReturn(httpClient);
		EasyMock.expect(httpClient.execute((HttpPost) EasyMock.anyObject())).andReturn(response);
		EasyMock.replay(httpService, httpClient);
		try {
			httpTranportService.doPost("http://www.google.com/", map);
		} catch (CoidiException e) {
			assertTrue(e.getMessage().contains("403"));
		}
		EasyMock.verify(httpService, httpClient);
	}

	@Before
	public void setUp() throws UnsupportedEncodingException {
		httpService = EasyMock.createMock(HttpService.class);
		httpClient = EasyMock.createMock(HttpClient.class);
		httpTranportService = new HttpTranportServiceImpl();
		httpTranportService.setHttpService(httpService);

		map = new HashMap<String, String>();
		pairList = new ArrayList<NameValuePair>();
		final StringEntity entity = new StringEntity("Ahoj");
		statusLike_200 = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpServletResponse.SC_OK, "Forbidden");
		statusLike_403 = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpServletResponse.SC_FORBIDDEN, "Forbidden");
		response = new BasicHttpResponse(statusLike_200);
		response.setEntity(entity);

	}

	@After
	public void tearDown() {
		httpService = null;
		httpTranportService = null;
		httpClient = null;
		map = null;
		pairList = null;
		response = null;
	}

}

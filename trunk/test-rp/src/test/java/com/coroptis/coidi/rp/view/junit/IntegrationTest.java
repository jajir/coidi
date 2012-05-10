package com.coroptis.coidi.rp.view.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.coroptis.coidi.rp.view.pages.AbstractIntegrationTest;
import com.coroptis.coidi.rp.view.pages.Normalizer;
import com.coroptis.coidi.rp.view.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.view.services.HttpService;

public class IntegrationTest extends AbstractIntegrationTest {

	public void testClient() throws Exception {
		// String id = "http://localhost:8080/user/juan";
//		String id = "http://jirout.myopenid.com/";
		String id = "http://kolecko.myid.net/";
		
		Normalizer normalizer = new Normalizer();
		id = normalizer.normalize(id, false);
		System.out.println(id);
		DiscoveryProcessor discovery = getService(DiscoveryProcessor.class);
		String endpoint = discovery.dicovery(id).getEndPoint();
		System.out.println("endpoint: " + endpoint);
		// assertEquals("http://localhost:8080/endpoint", endpoint);

		assertTrue(true);
	}

	public void testAssociation() throws Exception {
		HttpService httpService = getService(HttpService.class);
//		String endpoint = "http://www.myopenid.com/server";
//		String endpoint = "http://server.myid.net/server";
		String endpoint = "http://localhost:8080/openid";
		

		DiffieHellman dh = DiffieHellman.getDefault();

		Map<String, String> authRequest = new HashMap<String, String>();
		authRequest.put("openid.ns", "http://specs.openid.net/auth/2.0");
		authRequest.put("openid.mode", "associate");
		authRequest.put("openid.assoc_type", "HMAC-SHA1");
		authRequest.put("openid.session_type", "DH-SHA1");
		authRequest.put("openid.dh_consumer_public",
				Convert.convertToString(dh.getPublicKey()));
		authRequest.put("openid.dh_gen",
				Convert.convertToString(dh.getGenerator()));
		authRequest.put("openid.dh_modulus",
				Convert.convertToString(dh.getModulus()));
		authRequest.put("openid.mode", "associate");

		HttpPost post = new HttpPost(endpoint);
		post.getParams().setBooleanParameter(AllClientPNames.HANDLE_REDIRECTS,
				false);
		post.setEntity(new UrlEncodedFormEntity(toList(authRequest), "UTF-8"));
		HttpResponse httpResponse = httpService.getHttpClient().execute(post);
		String resp = EntityUtils.toString(httpResponse.getEntity());
		String statusLine = httpResponse.getStatusLine().getReasonPhrase();
		System.out.println(statusLine);
		System.out.println(resp);
	}

	public void testGetGoogle() throws Exception {
		HttpService httpService = getService(HttpService.class);
		HttpGet get = new HttpGet("http://www.google.com/");

		HttpResponse httpResponse = httpService.getHttpClient().execute(get);
		String out = EntityUtils.toString(httpResponse.getEntity());

		System.out.println(httpResponse.getStatusLine());
		System.out.println(out);
	}

	private List<NameValuePair> toList(Map<String, String> parameters) {
		List<NameValuePair> list = new ArrayList<NameValuePair>(
				parameters.size());
		for (Entry<String, String> entry : parameters.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

}
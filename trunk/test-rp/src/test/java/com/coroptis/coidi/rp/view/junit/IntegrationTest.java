package com.coroptis.coidi.rp.view.junit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.view.pages.AbstractIntegrationTest;
import com.coroptis.coidi.rp.view.pages.Normalizer;
import com.coroptis.coidi.rp.view.services.AssociationServise;
import com.coroptis.coidi.rp.view.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.view.services.HttpService;

public class IntegrationTest extends AbstractIntegrationTest {

	public void testClient() throws Exception {
		// String id = "http://localhost:8080/user/juan";
		// String id = "http://jirout.myopenid.com/";
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
		// String endpoint = "http://www.myopenid.com/server";
		// String endpoint = "http://server.myid.net/server";
		String endpoint = "http://localhost:8080/openid";
		AssociationServise associationServise = getService(AssociationServise.class);
		Association association = associationServise.generateAssociation(
				endpoint, SessionType.DH_SHA1, AssociationType.HMAC_SHA1);

		assertNotNull(association);
		logger.debug(association);
	}

	public void testGetGoogle() throws Exception {
		HttpService httpService = getService(HttpService.class);
		HttpGet get = new HttpGet("http://www.google.com/");

		HttpResponse httpResponse = httpService.getHttpClient().execute(get);
		String out = EntityUtils.toString(httpResponse.getEntity());

		System.out.println(httpResponse.getStatusLine());
		System.out.println(out);
	}

}
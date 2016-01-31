package com.coroptis.coidi.rp.junit.service;

import static org.easymock.EasyMock.expect;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;
import com.google.common.io.Files;

public class XrdsServiceTest extends AbstractLocalJunitTest {

    private final static String SERVICE_NAME = "realService";

    private XrdsService discoverySupport;

    public void testExtractDiscoveryResult() throws Exception {
	expect(services.getConvertorService().getInt("100")).andReturn(Integer.valueOf(100));
	expect(services.getConvertorService().getInt("10")).andReturn(Integer.valueOf(10));
	services.replay();
	DiscoveryResult ret = discoverySupport
		.extractDiscoveryResult(readFile("src/test/resources/example-xrds3.xml"));

	assertNotNull(ret);
	assertEquals("http://localhost:8080/user/juan", ret.getPreferedService().getLocalId());
	services.verify();
    }

    private String readFile(String fileName) throws IOException {
	String xrds = Files.toString(new File(fileName), Charset.forName("UTF-8"));
	return xrds;
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(XrdsService.class, XrdsServiceImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	discoverySupport = getService(SERVICE_NAME, XrdsService.class);
    }

    @Override
    protected void tearDown() throws Exception {
	discoverySupport = null;
	super.tearDown();
    }
}

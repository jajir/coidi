package com.coroptis.coidi.junit.op.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity.Gendre;
import com.coroptis.coidi.op.entities.IdentitySreg;
import com.coroptis.coidi.op.services.SregService;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.util.IdentityMock;
import com.coroptis.coidi.util.MapH;
import com.google.common.collect.Sets;

import junit.framework.TestCase;

public class SregServiceTest extends TestCase {

    private SregService service;

    public void test_isSreg11() throws Exception {
	assertTrue(service.isSreg11(new AuthenticationRequest(MapH.make("openid.sreg.optional",
		"nickname,email", "openid.ns.sreg", "http://openid.net/extensions/sreg/1.1"))));
	assertTrue(service.isSreg11(new AuthenticationRequest(
		MapH.make("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1"))));
	assertFalse(service.isSreg11(new AuthenticationRequest(MapH.make("openid.sreg.optional",
		"nickname,email", "openid.ns.sreg", "http://openid.net/somethig_else"))));
    }

    public void test_isSreg10() throws Exception {
	assertTrue(service.isSreg10(
		new AuthenticationRequest(MapH.make("openid.sreg.optional", "nickname,email"))));
	assertTrue(service.isSreg10(new AuthenticationRequest(MapH.make("openid.sreg.optional",
		"nickname,email", "openid.ns.sreg", "http://openid.net/somethig_else"))));
	assertTrue(service.isSreg10(new AuthenticationRequest(MapH.make("openid.sreg.required",
		"nickname,email", "openid.ns.sreg", "http://openid.net/somethig_else"))));
	assertFalse(service.isSreg10(new AuthenticationRequest(MapH.make("openid.sreg.optional",
		"nickname,email", "openid.ns.sreg", "http://openid.net/extensions/sreg/1.1"))));

	assertFalse(service.isSreg10(new AuthenticationRequest(MapH.make("as", "ss"))));
    }

    public void test_fillSregResponse() throws Exception {
	IdentitySreg identity = new IdentityMock();
	Set<String> fieldsToSign = new HashSet<String>();
	AuthenticationResponse response = new AuthenticationResponse();
	service.fillSregResponse(Sets.newHashSet(""), response, identity, fieldsToSign);

	assertTrue(fieldsToSign.isEmpty());
	// it's 2 because there are filled openid.mode and openid.ns
	assertEquals(2, response.getMap().size());
    }

    public void test_fillSregResponse_keysAreFilled() throws Exception {
	IdentitySreg identity = new IdentityMock();
	Set<String> fieldsToSign = new HashSet<String>();
	AuthenticationResponse response = new AuthenticationResponse();
	service.fillSregResponse(Sets.newHashSet("nickname", "email", "fullname", "dob", "gendre",
		"postcode", "country", "language", "timezone"), response, identity, fieldsToSign);

	assertTrue(fieldsToSign.isEmpty());
	// it's 2 because there are filled openid.mode and openid.ns
	assertEquals(2, response.getMap().size());
    }

    public void test_fillSregResponse_keysAreFilled_idenitityIsFilled() throws Exception {
	IdentityMock identity = new IdentityMock();
	identity.setNickname("kacer");
	identity.setEmail("kachna@hnizdo.cz");
	identity.setFullname("Kachna Obecna");
	identity.setDob(create(2003, 6, 23));
	identity.setGendre(Gendre.M);
	identity.setPostcode("123 99");
	identity.setCountry("es");
	identity.setLanguage("ES");
	identity.setTimezone("Prage/Europe");
	Set<String> fieldsToSign = new HashSet<String>();
	AuthenticationResponse response = new AuthenticationResponse();
	service.fillSregResponse(Sets.newHashSet("nickname", "email", "fullname", "dob", "gendre",
		"postcode", "country", "language", "timezone"), response, identity, fieldsToSign);

	assertTrue(fieldsToSign.contains("sreg.nickname"));
	assertTrue(fieldsToSign.contains("sreg.email"));
	assertTrue(fieldsToSign.contains("sreg.fullname"));
	assertTrue(fieldsToSign.contains("sreg.dob"));
	assertTrue(fieldsToSign.contains("sreg.gendre"));
	assertTrue(fieldsToSign.contains("sreg.postcode"));
	assertTrue(fieldsToSign.contains("sreg.country"));
	assertTrue(fieldsToSign.contains("sreg.language"));
	assertTrue(fieldsToSign.contains("sreg.timezone"));
	// it's 2 because there are filled openid.mode and openid.ns
	assertEquals(2 + 9, response.getMap().size());

	assertEquals("kacer", response.get("sreg.nickname"));
	assertEquals("kachna@hnizdo.cz", response.get("sreg.email"));
	assertEquals("Kachna Obecna", response.get("sreg.fullname"));
	assertEquals("2003-07-23", response.get("sreg.dob"));
	assertEquals("M", response.get("sreg.gendre"));
	assertEquals("123 99", response.get("sreg.postcode"));
	assertEquals("es", response.get("sreg.country"));
	assertEquals("ES", response.get("sreg.language"));
	assertEquals("Prage/Europe", response.get("sreg.timezone"));
    }

    private Date create(int year, int month, int day) {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, year);
	cal.set(Calendar.MONTH, month);
	cal.set(Calendar.DAY_OF_MONTH, day);
	return cal.getTime();
    }

    @Override
    protected void setUp() throws Exception {
	service = new SregServiceImpl();
	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	super.tearDown();
    }
}

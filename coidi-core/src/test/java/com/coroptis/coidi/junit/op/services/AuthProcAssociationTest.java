package com.coroptis.coidi.junit.op.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.impl.AuthProcAssociation;

public class AuthProcAssociationTest {

    private AuthProcAssociation authProc;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse response;
    private HttpSession userSession;
    private Set<String> fieldsToSign;

    private AssociationService associationService;

    @Test
    public void test_process_pass() throws Exception {
	authenticationRequest.setAssocHandle("assoc_handle_832169_392823");
	EasyMock.expect(associationService.isValid("assoc_handle_832169_392823")).andReturn(true);
	EasyMock.replay(associationService);
	authProc.process(authenticationRequest, response, userSession, fieldsToSign);

	assertTrue(fieldsToSign.contains("assoc_handle"));
	assertEquals("assoc_handle_832169_392823", response.getAssocHandle());
	assertNull(response.getInvalidateHandle());
	EasyMock.verify(associationService);
    }

    @Test
    public void test_process_invalid_handle() throws Exception {
	authenticationRequest.setAssocHandle("assoc_handle_832169_392823");
	EasyMock.expect(associationService.isValid("assoc_handle_832169_392823")).andReturn(false);
	EasyMock.replay(associationService);
	authProc.process(authenticationRequest, response, userSession, fieldsToSign);

	assertTrue(fieldsToSign.contains("assoc_handle"));
	assertEquals("assoc_handle_832169_392823", response.getInvalidateHandle());
	assertNull(response.getAssocHandle());
	EasyMock.verify(associationService);
    }

    @Before
    public void setUp() {
	associationService = EasyMock.createMock(AssociationService.class);
	authProc = new AuthProcAssociation(associationService);

	authenticationRequest = new AuthenticationRequest();
	response = new AuthenticationResponse();
	fieldsToSign = new HashSet<String>();
    }

    @After
    public void tearDown() {
	fieldsToSign = null;
	userSession = null;
	response = null;
	authenticationRequest = null;
	associationService = null;
	authProc = null;
    }

}

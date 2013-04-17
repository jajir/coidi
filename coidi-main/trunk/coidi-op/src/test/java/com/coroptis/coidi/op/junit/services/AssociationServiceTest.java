/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.junit.services;

import java.util.Calendar;
import java.util.Date;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.util.AbstractT5JunitTest;

public class AssociationServiceTest extends AbstractT5JunitTest {

    private final static String SERVICE_NAME = "realService";

    private AssociationService service;

    private Date timeToLive;

    private Association association;

    public void test_delete_assoc_exists() throws Exception {
	EasyMock.expect(services.getBaseAssociationDao().getByAssocHandle("assoc_handle_38#!"))
		.andReturn(association);
	services.getBaseAssociationDao().delete(association);
	services.replay();
	service.delete("assoc_handle_38#!");

	services.verify();
    }

    public void test_delete_assoc_not_exists() throws Exception {
	EasyMock.expect(services.getBaseAssociationDao().getByAssocHandle("assoc_handle_38#!"))
		.andReturn(null);
	services.replay();
	service.delete("assoc_handle_38#!");

	services.verify();
    }

    public void test_isValid_null_assocHandle() throws Exception {
	services.replay();
	assertFalse(service.isValid(null));

	services.verify();
    }

    public void test_isValid_notExisting_assocHandle() throws Exception {
	EasyMock.expect(services.getBaseAssociationDao().getByAssocHandle("assoc_handle_38#!"))
		.andReturn(null);
	services.replay();
	assertFalse(service.isValid("assoc_handle_38#!"));

	services.verify();
    }

    public void test_isValid_tooOldAssociation() throws Exception {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.HOUR, -1);
	association.setExpiredIn(cal.getTime());
	EasyMock.expect(services.getBaseAssociationDao().getByAssocHandle("assoc_handle_38#!"))
		.andReturn(association);
	services.replay();
	assertFalse(service.isValid("assoc_handle_38#!"));

	services.verify();
    }
    
    public void test_isValid_valid_association() throws Exception {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.HOUR, 1);
	association.setExpiredIn(cal.getTime());
	EasyMock.expect(services.getBaseAssociationDao().getByAssocHandle("assoc_handle_38#!"))
		.andReturn(association);
	services.replay();
	assertTrue(service.isValid("assoc_handle_38#!"));

	services.verify();
    }

    public void testCreateStateLessAssociation() throws Exception {
	mock_buildAssociation();
	EasyMock.expect(services.getAssociationTool().getDefaultAssociationType()).andReturn(
		AssociationType.HMAC_SHA1);
	byte[] key = new byte[] { 23, 23 };
	EasyMock.expect(
		services.getCryptoService().generateAssociationRandom(AssociationType.HMAC_SHA1))
		.andReturn(key);
	EasyMock.expect(services.getConvertorService().convertToString(key)).andReturn("23");
	services.replay();
	Association ret = service.createStateLessAssociation();

	assertEquals("random_code", ret.getAssocHandle());
	assertEquals(AssociationType.HMAC_SHA1, ret.getAssociationType());
	assertEquals(timeToLive, ret.getExpiredIn());
	assertEquals("23", ret.getMacKey());
	services.verify();
    }

    public void test_createAssociation() throws Exception {
	mock_buildAssociation();

	services.replay();
	Association ret = service.createAssociation(AssociationType.HMAC_SHA256,
		SessionType.DH_SHA256);

	services.verify();
	assertEquals(AssociationType.HMAC_SHA256, ret.getAssociationType());
	assertEquals(SessionType.DH_SHA256, ret.getSessionType());
    }

    public void test_createAssociation_missingAssocitionType() throws Exception {
	services.replay();
	try {
	    service.createAssociation(null, SessionType.DH_SHA256);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	services.verify();
    }

    public void test_createAssociation_missingSessionType() throws Exception {
	services.replay();
	try {
	    service.createAssociation(AssociationType.HMAC_SHA256, null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
	services.verify();
    }

    private void mock_buildAssociation() {
	EasyMock.expect(services.getBaseAssociationDao().createNewInstance())
		.andReturn(association);
	EasyMock.expect(services.getCryptoService().generateUUID()).andReturn("random_code");
	EasyMock.expect(services.getAssociationTool().getTimeToLive()).andReturn(timeToLive);
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AssociationService.class, AssociationServiceImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, AssociationService.class);
	timeToLive = new Date();
	association = new AssociationBean();
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	timeToLive = null;
	association = null;
	super.tearDown();
    }
}

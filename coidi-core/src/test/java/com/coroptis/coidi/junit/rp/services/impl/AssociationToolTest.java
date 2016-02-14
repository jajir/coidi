package com.coroptis.coidi.junit.rp.services.impl;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.impl.AssociationToolImpl;

public class AssociationToolTest {

    private OpConfigurationService configurationService;

    @Test(expected = NullPointerException.class)
    public void test_costructor_missing_op_configurationService() throws Exception {
	EasyMock.replay(configurationService);
	new AssociationToolImpl(null);
    }

    @Test(expected = CoidiException.class)
    public void test_costructor_default_association_type_missing() throws Exception {
	EasyMock.expect(configurationService.getDefaultAssociationType()).andReturn(null);
	EasyMock.replay(configurationService);
	new AssociationToolImpl(configurationService);
    }

    @Test(expected = CoidiException.class)
    public void test_costructor_default_association_type_invalid() throws Exception {
	EasyMock.expect(configurationService.getDefaultAssociationType()).andReturn("DH-SHA1")
		.times(3);
	EasyMock.replay(configurationService);
	new AssociationToolImpl(configurationService);
    }

    @Test(expected = NullPointerException.class)
    public void test_costructor_default_timeToLiveInSeconds_missing() throws Exception {
	EasyMock.expect(configurationService.getDefaultAssociationType())
		.andReturn(AssociationType.HMAC_SHA1.getName()).times(2);
	EasyMock.expect(configurationService.getAssociationTimeToLiveInSeconds()).andReturn(null);
	EasyMock.replay(configurationService);
	new AssociationToolImpl(configurationService);
    }

    @Test
    public void test_costructor_pass() throws Exception {
	EasyMock.expect(configurationService.getDefaultAssociationType())
		.andReturn(AssociationType.HMAC_SHA1.getName()).times(2);
	EasyMock.expect(configurationService.getAssociationTimeToLiveInSeconds()).andReturn(100)
		.times(1);
	EasyMock.replay(configurationService);
	AssociationToolImpl associationTool = new AssociationToolImpl(configurationService);

	assertNotNull(associationTool);
    }

    @Before
    public void setUp() {
	configurationService = EasyMock.createMock(OpConfigurationService.class);
    }

    @After
    public void tearDown() {
	EasyMock.verify(configurationService);
	configurationService = null;
    }
}

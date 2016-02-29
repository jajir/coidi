package com.coroptis.coidi.integration.rp.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.rp.iocsupport.RpBinding;
import com.coroptis.coidi.rp.services.RpConfigurationService;

public class RpBingingMock extends RpBinding {

    private final Association association = EasyMock.createMock(Association.class);
    private final Nonce nonce = EasyMock.createMock(Nonce.class);

    private final Object[] mocks = new Object[] { association, nonce };

    public RpBingingMock(RpConfigurationService conf) {
	super(conf);
    }

    /**
     * @return the association
     */
    public Association getAssociation() {
	return association;
    }

    public void replay() {
	EasyMock.replay(mocks);
    }

    public void verify() {
	EasyMock.verify(mocks);
    }

}

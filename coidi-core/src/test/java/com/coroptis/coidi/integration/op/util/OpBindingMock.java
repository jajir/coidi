package com.coroptis.coidi.integration.op.util;

import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;

import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.iocsupport.OpBinding;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.UserVerifier;

public class OpBindingMock extends OpBinding {

    private UserVerifier userVerifier;
    private BaseNonceDao baseNonceDao;
    private BaseAssociationDao baseAssociationDao;
    private BaseIdentityDao baseIdentityDao;
    private OpConfigurationService conf;
    private HttpSession httpSession;
    private Object[] mocks;
    private final Association association = EasyMock.createMock(Association.class);
    private final Nonce nonce = EasyMock.createMock(Nonce.class);

    public OpBindingMock(OpConfigurationService conf) {
	this.conf = conf;
	baseIdentityDao = EasyMock.createMock(BaseIdentityDao.class);
	baseAssociationDao = EasyMock.createMock(BaseAssociationDao.class);
	baseNonceDao = EasyMock.createMock(BaseNonceDao.class);
	userVerifier = EasyMock.createMock(UserVerifier.class);
	httpSession = EasyMock.createMock(HttpSession.class);
	mocks = new Object[] { baseIdentityDao, baseAssociationDao, baseNonceDao, userVerifier,
		httpSession, association, nonce };
    }

    @Override
    public UserVerifier getUserVerifier() {
	return userVerifier;
    }

    @Override
    public BaseNonceDao getBaseNonceDao() {
	return baseNonceDao;
    }

    @Override
    public BaseAssociationDao getBaseAssociationDao() {
	return baseAssociationDao;
    }

    @Override
    public BaseIdentityDao getBaseIdentityDao() {
	return baseIdentityDao;
    }

    @Override
    public OpConfigurationService getConf() {
	return conf;
    }

    public void replay() {
	EasyMock.replay(mocks);
    }

    public void verify() {
	EasyMock.verify(mocks);
    }

    /**
     * @return the httpSession
     */
    public HttpSession getHttpSession() {
	return httpSession;
    }

    /**
     * @return the association
     */
    public Association getAssociation() {
        return association;
    }

    /**
     * @return the nonce
     */
    public Nonce getNonce() {
        return nonce;
    }
}

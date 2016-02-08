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
package com.coroptis.coidi.op.util;

import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.CryptoService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.coroptis.coidi.op.services.UserVerifier;

public class Services {

    private final ConfService configurationService = EasyMock.createMock(ConfService.class);
    private final ConvertorService convertorService = EasyMock.createMock(ConvertorService.class);
    private final NonceService nonceService = EasyMock.createMock(NonceService.class);
    private final SigningService signingService = EasyMock.createMock(SigningService.class);
    private final StatelessModeNonceService statelessModeNonceService = EasyMock
	    .createMock(StatelessModeNonceService.class);
    private final AuthenticationProcessor authenticationProcessor = EasyMock
	    .createMock(AuthenticationProcessor.class);
    private final AuthenticationService authenticationService = EasyMock
	    .createMock(AuthenticationService.class);
    private final IdentityService identityService = EasyMock.createMock(IdentityService.class);
    private final NegativeResponseGenerator negativeResponseGenerator = EasyMock
	    .createMock(NegativeResponseGenerator.class);
    private final CryptoService cryptoService = EasyMock.createMock(CryptoService.class);
    private final AssociationTool associationTool = EasyMock.createMock(AssociationTool.class);
    private final BaseNonceDao baseNonceDao = EasyMock.createMock(BaseNonceDao.class);
    private final AssociationService associationService = EasyMock
	    .createMock(AssociationService.class);
    private final BaseAssociationDao baseAssociationDao = EasyMock
	    .createMock(BaseAssociationDao.class);
    private final RealmTool realmTool = EasyMock.createMock(RealmTool.class);
    private final OpenIdDispatcher openIdDispatcher11 = EasyMock.createMock(OpenIdDispatcher.class);
    private final OpenIdDispatcher openIdDispatcher20 = EasyMock.createMock(OpenIdDispatcher.class);
    private final OpenIdRequestTool openIdRequestTool = EasyMock
	    .createMock(OpenIdRequestTool.class);
    private final OpConfigurationService opConfigurationService = EasyMock
	    .createMock(OpConfigurationService.class);
    private final HttpSession httpSession = EasyMock.createMock(HttpSession.class);
    private final UserVerifier userVerifier = EasyMock.createMock(UserVerifier.class);

    private final Object[] mocks = new Object[] { getBaseNonceDao(), getConfigurationService(),
	    getNonceService(), getSigningService(), getStatelessModeNonceService(),
	    getAuthenticationProcessor(), getAuthenticationService(), getIdentityService(),
	    getNegativeResponseGenerator(), getHttpSession(), getConvertorService(),
	    getCryptoService(), getAssociationTool(), getAssociationService(),
	    getBaseAssociationDao(), getRealmTool(), getOpenIdDispatcher11(),
	    getOpenIdDispatcher20(), getOpenIdRequestTool(), getOpConfigurationService(),
	    getUserVerifier() };

    private static Services services;

    public static Services getServices() {
	if (services == null) {
	    services = new Services();
	}
	return services;
    }

    private Services() {
    }

    public void reset() {
	for (Object object : mocks) {
	    EasyMock.reset(object);
	}
    }

    public void verify() {
	for (Object object : mocks) {
	    EasyMock.verify(object);
	}
    }

    public void replay() {
	for (Object object : mocks) {
	    EasyMock.replay(object);
	}
    }

    /**
     * @return the configurationService
     */
    public ConfService getConfigurationService() {
	return configurationService;
    }

    /**
     * @return the nonceService
     */
    public NonceService getNonceService() {
	return nonceService;
    }

    /**
     * @return the signingService
     */
    public SigningService getSigningService() {
	return signingService;
    }

    /**
     * @return the statelessModeNonceService
     */
    public StatelessModeNonceService getStatelessModeNonceService() {
	return statelessModeNonceService;
    }

    /**
     * @return the authenticationProcessor
     */
    public AuthenticationProcessor getAuthenticationProcessor() {
	return authenticationProcessor;
    }

    /**
     * @return the authenticationService
     */
    public AuthenticationService getAuthenticationService() {
	return authenticationService;
    }

    /**
     * @return the identityService
     */
    public IdentityService getIdentityService() {
	return identityService;
    }

    /**
     * @return the negativeResponseGenerator
     */
    public NegativeResponseGenerator getNegativeResponseGenerator() {
	return negativeResponseGenerator;
    }

    /**
     * @return the convertorService
     */
    public ConvertorService getConvertorService() {
	return convertorService;
    }

    /**
     * @return the cryptoService
     */
    public CryptoService getCryptoService() {
	return cryptoService;
    }

    /**
     * @return the associationTool
     */
    public AssociationTool getAssociationTool() {
	return associationTool;
    }

    /**
     * @return the baseNonceDao
     */
    public BaseNonceDao getBaseNonceDao() {
	return baseNonceDao;
    }

    /**
     * @return the associationService
     */
    public AssociationService getAssociationService() {
	return associationService;
    }

    /**
     * @return the baseAssociationDao
     */
    public BaseAssociationDao getBaseAssociationDao() {
	return baseAssociationDao;
    }

    /**
     * @return the realmTool
     */
    public RealmTool getRealmTool() {
	return realmTool;
    }

    /**
     * @return the openIdDispatcher11
     */
    public OpenIdDispatcher getOpenIdDispatcher11() {
	return openIdDispatcher11;
    }

    /**
     * @return the openIdDispatcher20
     */
    public OpenIdDispatcher getOpenIdDispatcher20() {
	return openIdDispatcher20;
    }

    /**
     * @return the openIdRequestTool
     */
    public OpenIdRequestTool getOpenIdRequestTool() {
	return openIdRequestTool;
    }

    /**
     * @return the opConfigurationService
     */
    public OpConfigurationService getOpConfigurationService() {
	return opConfigurationService;
    }

    /**
     * @return the httpSession
     */
    public HttpSession getHttpSession() {
	return httpSession;
    }

    /**
     * @return the userVerifier
     */
    public UserVerifier getUserVerifier() {
	return userVerifier;
    }
}
package com.coroptis.coidi.op.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.AssociationProcessor;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.CryptoService;

public class AssociationProcessorImpl implements AssociationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private BaseAssociationDao baseAssociationDao;

    @Inject
    private CryptoSessionService cryptoSessionService;

    @Inject
    private CryptographyService cryptographyService;

    @Inject
    private CryptoService cryptoService;

    @Inject
    private AssociationService associationService;;

    @Inject
    private ConvertorService convertorService;

    @Override
    public AbstractMessage processAssociation(final AssociationRequest request,
	    final SessionType sessionType, final AssociationType associationType) {
	Association association = associationService
		.createAssociation(associationType, sessionType);
	AssociationResponse out = new AssociationResponse();
	if (association.getSessionType().equals(SessionType.NO_ENCRYPTION)) {
	    logger.info("No encryption was setup during association request/response.");
	    byte macKey[] = cryptoService.generateAssociationRandom(association
		    .getAssociationType());
	    association.setMacKey(convertorService.convertToString(macKey));
	    out.setMacKey(macKey);
	} else {
	    association.setMacKey(convertorService.convertToString(cryptoService
		    .generateSessionRandom(association.getSessionType())));
	    KeyPair cryptoSession = cryptoSessionService.generateCryptoSession(request);
	    out.setEncMacKey(cryptographyService.encryptSecret(cryptoSession,
		    request.getDhConsumerPublic(),
		    convertorService.convertToBytes(association.getMacKey()), sessionType));
	    out.setDhServerPublic(cryptoSession.getPublicKey());
	}

	baseAssociationDao.create(association);

	out.setSessionType(association.getSessionType());
	out.setAssociationType(association.getAssociationType());
	out.setAssocHandle(association.getAssocHandle());
	out.setExpiresIn(String.valueOf((association.getExpiredIn().getTime() - System
		.currentTimeMillis()) / 1000L));
	return out;
    }

}

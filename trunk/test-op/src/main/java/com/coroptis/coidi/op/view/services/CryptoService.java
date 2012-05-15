package com.coroptis.coidi.op.view.services;

import com.coroptis.coidi.core.message.OpenIdRequestAssociation;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.view.utils.KeyPair;

public interface CryptoService {

	byte[] generateSessionRandom(SessionType sessionType);

	byte[] generateAssociationRandom(AssociationType associationType);

	String generateUUID();

	KeyPair generateCryptoSession(OpenIdRequestAssociation association);

}

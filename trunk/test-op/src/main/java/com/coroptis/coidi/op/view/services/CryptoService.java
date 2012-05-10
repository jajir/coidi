package com.coroptis.coidi.op.view.services;

import com.coroptis.coidi.op.view.entities.Association;
import com.coroptis.coidi.op.view.utils.KeyPair;

public interface CryptoService {

	byte[] generateSessionRandom(Association.SessionType sessionType);

	byte[] generateAssociationRandom(Association.AssociationType associationType);

	String generateUUID();

	KeyPair generateCryptoSession(OpenIdRequestAssociation association);
	

}

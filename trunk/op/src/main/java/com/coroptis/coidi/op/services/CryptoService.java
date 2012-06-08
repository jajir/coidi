package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface CryptoService {

	byte[] generateSessionRandom(SessionType sessionType);

	byte[] generateAssociationRandom(AssociationType associationType);

	String generateUUID();

}

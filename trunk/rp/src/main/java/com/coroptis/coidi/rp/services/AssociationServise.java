package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface AssociationServise {

	Association generateAssociation(String opEndpoint, SessionType sessionType,
			AssociationType associationType);

}

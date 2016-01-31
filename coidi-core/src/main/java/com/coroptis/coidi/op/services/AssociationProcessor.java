package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface AssociationProcessor {

    AbstractMessage processAssociation(AssociationRequest request, SessionType sessionType,
	    AssociationType associationType);

}

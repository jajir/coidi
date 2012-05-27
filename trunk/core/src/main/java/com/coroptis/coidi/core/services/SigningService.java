package com.coroptis.coidi.core.services;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;

public interface SigningService {

	String sign(final AbstractMessage response, final String macKey,
			final AssociationType associationType);

	String sign(AbstractMessage response, Association association);

}

package com.coroptis.coidi.conf.services;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.entities.Association;

public interface SigningService {

	String sign(AbstractMessage response, Association association);

}

package com.coroptis.coidi.op.view.services.impl;

import org.apache.log4j.Logger;

import com.coroptis.coidi.op.view.entities.Association;
import com.coroptis.coidi.op.view.services.AssociationService;

public class AssociationServiceImpl implements AssociationService {

	private final Logger logger = Logger
			.getLogger(AssociationServiceImpl.class);

	@Override
	public void create(Association association) {
		logger.debug("creating: " + association);
		// TODO finish that
	}

}

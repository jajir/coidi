package com.coroptis.coidi.op.view.services.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.view.entities.AssociationImpl;
import com.coroptis.coidi.op.view.services.AssociationService;

public class AssociationServiceImpl implements AssociationService {

	private final Logger logger = Logger
			.getLogger(AssociationServiceImpl.class);

	@Inject
	private Session session;

	@Override
	public void create(AssociationImpl association) {
		logger.debug("creating: " + association);
		session.save(association);
	}

	@Override
	public AssociationImpl getByAssocHandle(String assoc_handle) {
		return (AssociationImpl) session.createCriteria(AssociationImpl.class)
				.add(Restrictions.eq("assocHandle", assoc_handle))
				.uniqueResult();
	}

}

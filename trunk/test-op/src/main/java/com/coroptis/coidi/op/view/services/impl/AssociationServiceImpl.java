package com.coroptis.coidi.op.view.services.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.view.entities.Association;
import com.coroptis.coidi.op.view.services.AssociationService;

public class AssociationServiceImpl implements AssociationService {

	private final Logger logger = Logger
			.getLogger(AssociationServiceImpl.class);

	@Inject
	private Session session;

	@Override
	@CommitAfter
	public void create(Association association) {
		logger.debug("creating: " + association);
		session.save(association);
	}

	@Override
	public Association getByAssocHandle(String assoc_handle) {
		return (Association) session.createCriteria(Association.class).add(
				Restrictions.eq("assocHandle", assoc_handle)).uniqueResult();
	}

}

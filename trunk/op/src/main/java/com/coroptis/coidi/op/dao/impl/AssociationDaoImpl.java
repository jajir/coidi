package com.coroptis.coidi.op.dao.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.AssociationImpl;

public class AssociationDaoImpl implements AssociationDao {

	@Inject
	private Logger logger;

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

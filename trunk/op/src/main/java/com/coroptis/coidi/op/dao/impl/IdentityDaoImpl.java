package com.coroptis.coidi.op.dao.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.entities.Identity;

public class IdentityDaoImpl implements IdentityDao {

	@Inject
	private Session session;

	@Override
	public Identity getIdentityByName(final String idIdentity) {
		return (Identity) session.createCriteria(Identity.class)
				.add(Restrictions.eq("idIdentity", idIdentity)).uniqueResult();
	}

}

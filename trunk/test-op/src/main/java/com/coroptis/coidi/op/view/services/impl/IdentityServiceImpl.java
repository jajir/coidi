package com.coroptis.coidi.op.view.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.view.entities.Identity;
import com.coroptis.coidi.op.view.services.IdentityService;

public class IdentityServiceImpl implements IdentityService {

	@Inject
	private Session session;

	@Override
	public Identity getIdentityByName(final String idIdentity) {
		return (Identity) session.createCriteria(Identity.class)
				.add(Restrictions.eq("idIdentity", idIdentity)).uniqueResult();
	}

}

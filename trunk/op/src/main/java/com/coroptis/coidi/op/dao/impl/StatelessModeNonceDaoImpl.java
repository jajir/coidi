package com.coroptis.coidi.op.dao.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.entities.StatelessModeNonce;

public class StatelessModeNonceDaoImpl implements StatelessModeNonceDao {

	@Inject
	private Session session;

	@Override
	public void save(StatelessModeNonce statelessModeNonce) {
		session.save(statelessModeNonce);
	}

	@Override
	public StatelessModeNonce getByNonce(String noce) {
		return (StatelessModeNonce) session
				.createCriteria(StatelessModeNonce.class)
				.add(Restrictions.eq("nonce", noce)).uniqueResult();
	}

}

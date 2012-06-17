package com.coroptis.coidi.op.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.IdentityService;

public class IdentityServiceImpl implements IdentityService {

	@Inject
	private IdentityDao identityDao;

	@Override
	public Identity getIdentityByName(final String idIdentity) {
		return identityDao.getIdentityByName(idIdentity);
	}
}

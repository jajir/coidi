/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.services.impl;

import javax.inject.Inject;

import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.dao.BaseUserDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.User;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.IdentityService;
import com.google.common.base.Preconditions;

public class IdentityServiceImpl implements IdentityService {

    @Inject
    private BaseIdentityDao identityDao;

    @Inject
    private BaseUserDao userDao;

    @Inject
    private IdentityNamesConvertor identityNamesConvertor;

    @Override
    public Identity getByIdentityId(final String identityId) {
	Preconditions.checkNotNull(identityId, "identityId is null");
	return identityDao.getIdentityId(identityId);
    }

    @Override
    public Identity getByOpLocalIdentifier(final String opLocalIdentifier) {
	Preconditions.checkNotNull(opLocalIdentifier, "opLocalIdentifier is null");
	if (identityNamesConvertor.isOpLocalIdentifier(opLocalIdentifier)) {
	    return identityDao.getIdentityId(identityNamesConvertor
		    .convertToIdentityId(opLocalIdentifier));
	} else {
	    return null;
	}
    }

    @Override
    public Boolean isIdentityLogged(final UserSessionSkeleton userSession,
	    final Identity claimedIdentity) {
	Preconditions.checkNotNull(claimedIdentity, "claimedIdentity is null");
	if (userSession == null) {
	    return false;
	}
	User user = userDao.getById(userSession.getIdUser());
	if (user == null) {
	    return false;
	}
	for (final Identity identity : user.getIdentities()) {
	    if (identity.equals(claimedIdentity)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public Boolean isUsersOpIdentifier(final Object idUser, final String opLocalIdentifier) {
	final User user = Preconditions.checkNotNull(userDao.getById(idUser), "user is null");
	final String identityId = identityNamesConvertor.convertToIdentityId(opLocalIdentifier);
	Preconditions.checkNotNull(identityId, "opLocalIdentifier is null");

	for (Identity identity : user.getIdentities()) {
	    if (identityId.equals(identity.getIdIdentity())) {
		return true;
	    }
	}
	return false;
    }

}

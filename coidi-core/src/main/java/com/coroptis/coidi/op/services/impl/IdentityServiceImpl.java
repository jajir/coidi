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
import javax.inject.Singleton;

import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.IdentityService;
import com.google.common.base.Preconditions;

@Singleton
public class IdentityServiceImpl implements IdentityService {

    @Inject
    private BaseIdentityDao identityDao;

    @Inject
    private IdentityNamesConvertor identityNamesConvertor;

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


}

package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestTool;

/**
 * Implementation
 * 
 * @author jirout
 * 
 */
public class OpenIdRequestToolImpl implements OpenIdRequestTool {

    @Inject
    private IdentityService identityService;

    @Inject
    private Logger logger;

    @Override
    public boolean isOpenIdVersion20(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) != null
		&& AbstractMessage.OPENID_NS_20.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS));
    }

    @Override
    public boolean isOpenIdVersion1x(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) == null
		|| AbstractMessage.OPENID_NS_10.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS))
		|| AbstractMessage.OPENID_NS_11.equals(requestParams
			.get(OpenIdDispatcher.OPENID_NS));
    }

    @Override
    public boolean verify(final String opLocalIdentity, final UserSessionSkeleton session) {
	Identity identity = identityService.getByOpLocalIdentifier(opLocalIdentity);
	if (identity == null) {
	    logger.debug("Requested identity '" + opLocalIdentity + "' doesn't exists.");
	    return false;
	}
	if (!identityService.isUsersOpIdentifier(session.getIdUser(), opLocalIdentity)) {
	    logger.debug("Identity '" + opLocalIdentity + "' doesn't belongs to user '"
		    + session.getIdUser() + "'.");
	    return false;
	}
	return true;
    }

}

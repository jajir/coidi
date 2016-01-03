package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.AuthRespSupport;

public class AuthRespSupportImpl implements AuthRespSupport {

    private final static String NAME_SPACE_PREFIX = "openid.ns.";

    @Override
    public String getNameSpacePrefix(final String key) {
	if (key.startsWith(NAME_SPACE_PREFIX)) {
	    return key.substring(NAME_SPACE_PREFIX.length());
	} else {
	    throw new CoidiException("String '" + key + "' is not valid namespace ");
	}
    }
}

package com.coroptis.coidi.op.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.base.UserSessionSkeleton;

public abstract class AbstractOpenIdDispatcher implements OpenIdDispatcher {

    protected final List<OpenIdDispatcher> dispatchers = new ArrayList<OpenIdDispatcher>();

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final UserSessionSkeleton userSession) {
	for (final OpenIdDispatcher builder : dispatchers) {
	    final AbstractMessage row = builder.process(requestParams, userSession);
	    if (row != null) {
		return row;
	    }
	}
	return null;
    }

}

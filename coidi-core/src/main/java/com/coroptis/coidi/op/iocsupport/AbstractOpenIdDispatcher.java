package com.coroptis.coidi.op.iocsupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
@Singleton
public abstract class AbstractOpenIdDispatcher implements OpenIdDispatcher {

    protected final List<OpenIdDispatcher> dispatchers = new ArrayList<OpenIdDispatcher>();

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final HttpSession userSession) {
	for (final OpenIdDispatcher builder : dispatchers) {
	    final AbstractMessage row = builder.process(requestParams, userSession);
	    if (row != null) {
		return row;
	    }
	}
	return null;
    }

}

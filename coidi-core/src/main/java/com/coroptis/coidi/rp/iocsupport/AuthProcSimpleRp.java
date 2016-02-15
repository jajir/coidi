package com.coroptis.coidi.rp.iocsupport;

import javax.inject.Inject;
import javax.inject.Named;

import com.coroptis.coidi.core.util.AbstractAuthReq;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthProcSimpleRp extends AbstractAuthReq {

    @Inject
    public AuthProcSimpleRp(

	    final @Named("authReqPreconditions") AuthReq authReqPreconditions,
	    final @Named("authReqUiIcon") AuthReq auReqUiIcon,
	    final @Named("authReqTerminator") AuthReq authReqTerminator

    ) {

	dispatchers.add(authReqPreconditions);
	dispatchers.add(auReqUiIcon);
	dispatchers.add(authReqTerminator);
    }

}

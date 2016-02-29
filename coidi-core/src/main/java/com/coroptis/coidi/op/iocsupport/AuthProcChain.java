package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.core.util.AbstractAuthProc;
import com.coroptis.coidi.op.services.AuthProc;
import com.google.common.base.Preconditions;

public class AuthProcChain extends AbstractAuthProc {

    public void add(final AuthProc authProc) {
	dispatchers.add(Preconditions.checkNotNull(authProc));
    }

}

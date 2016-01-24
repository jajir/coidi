package com.coroptis.coidi.op.junit;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.AuthProcCheckIdSetup20;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.google.common.base.Objects;

public class AuthProcCheckIdSetup20Test {

    Logger logger = LoggerFactory.getLogger(AuthProcCheckIdSetup20Test.class);

    /**
     * Test verify that order in which are processors executed is correct. See
     * command line output.
     * 
     * @throws Exception
     */
    @Test
    public void test_authentication_processors_order() throws Exception {
	AuthProcCheckIdSetup20 service = new AuthProcCheckIdSetup20(make("authProcSreg10"),
		make("authProcSreg11"), make("authProcSign"), make("authProcVerifyLoggedUser"),
		make("authProcVerifyIdentitySelect20"), make("authProcVerifyIdentity20"),
		make("authProcAssociation"), make("authProcStateLessAssociation"),
		make("authProcNonce"), make("authProcResponse20"));

	assertNotNull(service);
	service.process(null, null, null, null);
    }

    private MockAuthProc make(final String code) {
	return new MockAuthProc(code);
    }

    private class MockAuthProc implements AuthenticationProcessor {

	private final String code;

	private MockAuthProc(final String code) {
	    this.code = code;
	}

	@Override
	public String toString() {
	    return Objects.toStringHelper(MockAuthProc.class).add("code", code).toString();
	}

	@Override
	public AbstractMessage process(AuthenticationRequest authenticationRequest,
		AuthenticationResponse response, UserSessionSkeleton userSession,
		Set<String> fieldsToSign) {
	    logger.debug("code: {}", code);
	    return null;
	}

    }

}

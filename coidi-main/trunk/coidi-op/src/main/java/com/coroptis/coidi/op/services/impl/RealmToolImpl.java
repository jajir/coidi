package com.coroptis.coidi.op.services.impl;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.util.RealmRequest;
import com.google.common.base.Preconditions;

public class RealmToolImpl implements RealmTool {

    @Inject
    private OpenIdRequestTool openIdRequestTool;

    private final boolean wildCardEnabled;

    public RealmToolImpl(
	    @Inject @Symbol(KEY_IS_WILD_CARD_IN_REALM_ENABLED) final boolean wildCardEnabled) {
	this.wildCardEnabled = wildCardEnabled;
    }

    @Override
    public boolean isMatching(final String realmPattern, final String returnTo) {
	Preconditions.checkNotNull(realmPattern, "realmPattern is null");
	Preconditions.checkNotNull(returnTo, "returnTo is null");
	if (realmPattern.indexOf("#") > 0) {
	    return false;
	}
	if (wildCardEnabled) {
	    String adjustedPattern = realmPattern + "*";
	    final Pattern p = Pattern.compile(adjustedPattern.replace("*", ".*"));
	    return p.matcher(returnTo).matches();
	} else {
	    return returnTo.startsWith(realmPattern);
	}
    }

    public RealmRequest createRealmRequest(final Map<String, String> parameters) {
	AuthenticationRequest request = new AuthenticationRequest(parameters);
	if (openIdRequestTool.isOpenIdVersion1x(parameters)) {
	    request.setRealm(request.getTrustRoot());
	}
	RealmRequest out = new RealmRequest();
	if (StringUtils.isBlank(request.getRealm())) {
	    out.setRealmPattern(request.getReturnTo());
	} else {
	    out.setRealmPattern(request.getRealm());
	}
	out.setUrl(request.getReturnTo());
	if (!isMatching(out.getRealmPattern(), out.getUrl())) {
	    return null;
	}
	return out;
    }

}

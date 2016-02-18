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

import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang.StringUtils;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.util.RealmRequest;
import com.google.common.base.Preconditions;
@Singleton
public class RealmToolImpl implements RealmTool {

    @Inject
    private OpenIdRequestTool openIdRequestTool;

    private final boolean wildCardEnabled;

    @Inject
    public RealmToolImpl(final OpConfigurationService configurationService) {
	this.wildCardEnabled = configurationService.isWildcardAllowedInRealm();
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

    @Override
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

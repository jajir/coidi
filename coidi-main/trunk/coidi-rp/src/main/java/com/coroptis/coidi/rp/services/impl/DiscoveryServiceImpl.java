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
package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoveryService;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.google.common.base.Preconditions;

public class DiscoveryServiceImpl implements DiscoveryService {

    @Inject
    private DiscoveryProcessor discoveryProcessor;

    @Inject
    private DiscoverySupport discoverySupport;

    @Override
    public DiscoveryResult dicovery(final String userSuppliedId) {
	Preconditions.checkNotNull(userSuppliedId, "userSuppliedId");
	String claimedId = userSuppliedId.trim();
	if (!discoverySupport.isItEmail(claimedId)) {
	    claimedId = discoverySupport.normalize(claimedId);
	}
	DiscoveryResult out = discoveryProcessor.dicovery(claimedId);
	out.setClaimedId(claimedId);
	return out;
    }

}

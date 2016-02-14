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

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Implementation relay on configuration.
 * 
 * @author jirout
 * 
 */
@Singleton
public class AssociationToolImpl implements AssociationTool {

    private final Integer timeToLiveInSeconds;

    /**
     * This value is initialized in constructor, because it's good to know that
     * application is not correctly configured at the beginning.
     */
    private final AssociationType defaultAssociationType;

    @Inject
    public AssociationToolImpl( // NO_UCD
	    final OpConfigurationService configurationService) {
	Preconditions.checkNotNull(configurationService, "OP configuration service is null");
	if (Strings.isNullOrEmpty(configurationService.getDefaultAssociationType())) {
	    throw new CoidiException("Default association type is empty");
	}
	defaultAssociationType = AssociationType
		.convert(configurationService.getDefaultAssociationType());
	if (defaultAssociationType == null) {
	    throw new CoidiException("Invalid default association type value '"
		    + configurationService.getDefaultAssociationType() + "'");
	}
	this.timeToLiveInSeconds = Preconditions.checkNotNull(
		configurationService.getAssociationTimeToLiveInSeconds(),
		"Configuration association time to live in seconds is empty");
    }

    @Override
    public Date getTimeToLive() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.SECOND, timeToLiveInSeconds);
	return cal.getTime();
    }

    @Override
    public AssociationType getDefaultAssociationType() {
	return defaultAssociationType;
    }

    @Override
    public Boolean isPrivateAssociation(final Association association) {
	Preconditions.checkNotNull(association, "association is null");
	return association.getSessionType() == null;
    }

}

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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.services.AssociationTool;
import com.google.common.base.Preconditions;

/**
 * Implementation relay on configuration.
 * 
 * @author jirout
 * 
 */
public class AssociationToolImpl implements AssociationTool {

    @Inject
    @Symbol(DEFAULT_TIME_TO_LIVE_IN_SECONDS)
    private Integer timeToLiveInSeconds;

    @SuppressWarnings("unused")
    private final Logger logger;

    /**
     * This value is initialized in constructor, because it's good to know that
     * application is not correctly configured at the beginning.
     */
    private final AssociationType defaultAssociationType;

    public AssociationToolImpl( // NO_UCD
	    @Inject @Symbol(DEFAULT_ASSOCITION_TYPE) final String assocTypeStr, final Logger logger) {
	defaultAssociationType = AssociationType.convert(assocTypeStr);
	this.logger = logger;
	logger.debug("stateless mode association type: " + defaultAssociationType);
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

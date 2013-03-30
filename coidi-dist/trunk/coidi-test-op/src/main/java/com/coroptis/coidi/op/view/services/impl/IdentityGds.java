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
package com.coroptis.coidi.op.view.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.entities.Identity;

public class IdentityGds implements GridDataSource {

    private final static Logger logger = Logger.getLogger(IdentityGds.class);

    @Inject
    private BaseIdentityDao identityDao;

    private List<Identity> currentPage;

    @Override
    public int getAvailableRows() {
	return identityDao.getCount();
    }

    @Override
    public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) {
	logger.debug("start: " + startIndex);
	logger.debug("end  : " + endIndex);
	currentPage = identityDao.getChunk(startIndex, endIndex);
    }

    @Override
    public Object getRowValue(int index) {
	logger.debug("max: " + currentPage.size() + ", getting: " + index);
	return currentPage.get(index);
    }

    @Override
    public Class<Identity> getRowType() {
	return Identity.class;
    }

}

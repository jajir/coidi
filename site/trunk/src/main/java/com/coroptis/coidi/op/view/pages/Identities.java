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
package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Service;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

public class Identities {

    @SuppressWarnings("unused")
    @Inject
    @Service("identityGds")
    @Property
    private GridDataSource source;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private ComponentResources resources;

    @SuppressWarnings("unused")
    @Property
    private com.coroptis.coidi.op.entities.Identity identity;

    public BeanModel<com.coroptis.coidi.op.entities.Identity> getModel() {
	BeanModel<com.coroptis.coidi.op.entities.Identity> model = beanModelSource
		.createDisplayModel(com.coroptis.coidi.op.entities.Identity.class,
			resources.getMessages());
	return model;
    }
}

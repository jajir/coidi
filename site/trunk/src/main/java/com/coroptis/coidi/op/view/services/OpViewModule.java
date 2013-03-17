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
package com.coroptis.coidi.op.view.services;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.op.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.op.view.services.impl.IdentityGds;

public class OpViewModule {// NO_UCD

    public static void bind(ServiceBinder binder) {
	binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
		"accessControllerDispatcher");
	binder.bind(GridDataSource.class, IdentityGds.class).withId("identityGds");
    }

    public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
	    @InjectService("accessControllerDispatcher") Dispatcher accessController) {
	configuration.add("accessControllerDispatcher", accessController, "before:PageRender");
    }

    public static void contributeHibernateEntityPackageManager(Configuration<String> conf) {
	conf.add("com.coroptis.coidi.op.entities");
    }

}

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
package com.coroptis.coidi.core.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.internal.DefaultModuleDefImpl;
import org.apache.tapestry5.ioc.internal.services.PlasticProxyFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.services.CoreModule;

/**
 * This filter allows us to jump into startup process and put in hand
 * {@link Registry}.
 * 
 * @author jan
 * 
 */
public class ExtendedTapestryFilter extends TapestryFilter {

    private Logger logger = LoggerFactory.getLogger(ExtendedTapestryFilter.class);

    @SuppressWarnings("unchecked")
    private List<Class<?>> extract(ServletContext context) throws ClassNotFoundException {
	List<Class<?>> models = new ArrayList<Class<?>>();
	Enumeration<String> sttrNames = context.getInitParameterNames();
	while (sttrNames.hasMoreElements()) {
	    String attrName = sttrNames.nextElement();
	    if (attrName.startsWith("additional.module.")) {
		Class<?> c = Class.forName((String) context.getInitParameter(attrName));
		logger.debug("adding additional module: " + c.getName());
		models.add(c);
	    }
	}
	return models;
    }

    /**
     * Overridden in subclasses to provide additional module definitions beyond
     * those normally located. This implementation returns an empty array.
     */
    @Override
    protected ModuleDef[] provideExtraModuleDefs(ServletContext context) {
	final String prop = context.getInitParameter("system.property.configuration.directory");
	if (prop == null) {
	    logger.warn("Web application context param 'system.property.configuration.directory' wasn't defined.");
	} else {
	    System.setProperty("system.property.configuration.directory", prop);
	}
	try {
	    List<Class<?>> models = extract(context);
	    ModuleDef[] out = new ModuleDef[models.size() + 1];
	    for (int i = 0; i < models.size(); i++) {
		out[i + 1] = new DefaultModuleDefImpl(models.get(i), logger,
			new PlasticProxyFactoryImpl(ExtendedTapestryFilter.class.getClassLoader(),
				logger));
	    }
	    out[0] = new DefaultModuleDefImpl(CoreModule.class, logger,
		    new PlasticProxyFactoryImpl(ExtendedTapestryFilter.class.getClassLoader(),
			    logger));
	    return out;
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }
}

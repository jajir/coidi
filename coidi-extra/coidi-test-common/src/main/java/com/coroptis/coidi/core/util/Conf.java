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

import java.io.File;

import com.coroptis.coidi.core.services.ConfService;
import com.coroptis.coidi.core.services.ConfigurationException;

/**
 * Class that allows to access configuration feature in a static way. It should
 * not be called from normal code.
 * 
 * @author jan
 * 
 */
public class Conf {

    /**
     * Method that get directory where is stored application configuration.
     * Currently file <code>configuration-...xml</code> and
     * <code>log4j-...xml</code>.
     * <p>
     * This method is public static, because log4j system have to loaded in
     * static way before T5 application starts to boot up.
     * </p>
     * 
     * @param systemPropertyConfigurationDirectory
     *            optional system property name where is stored configuration
     *            directory
     * @return configuration directory
     */
    public static String getConfigurationDirectory(
	    final String systemPropertyConfigurationDirectory) {
	if (systemPropertyConfigurationDirectory == null) {
	    throw new ConfigurationException(
		    "name of system property containing congiguration directory is null");
	}
	String confDir = System.getProperty(systemPropertyConfigurationDirectory);
	if (confDir == null || confDir.length() == 0) {
	    return ConfService.DEFAULT_CONFIGURATION_DIRECTORY;
	} else {
	    if (!confDir.endsWith("/") && !confDir.endsWith("\\")) {
		confDir = confDir + File.separator;
	    }
	    return confDir;
	}
    }

    /**
     * @return Value of server.role property or it's default value if not
     *         defined.
     */
    public static String getServerRole() {
	String value = System.getProperty(ConfService.SYSTEM_PROPERTY_SERVER_ROLE);
	if (value != null) {
	    return value;
	}
	return ConfService.DEFAULT_SERVER_ROLE;
    }

}

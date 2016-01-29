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
package com.coroptis.coidi.core.services;

import java.util.Map;
import java.util.Properties;

import org.apache.tapestry5.ioc.Resource;

/**
 * This service allows to obtain basic access to application configuration.
 * 
 * @author jan
 * 
 */
public interface ConfigurationService {

    /**
     * When server.role is not specified in system properties this default value
     * is taken.
     */
    static final String DEFAULT_SERVER_ROLE = "local";

    /**
     * Under this key is server role loaded from system properties.
     */
    static final String SYSTEM_PROPERTY_SERVER_ROLE = "server.role";

    /**
     * Default directory in class path where should be application
     * configuration.
     */
    public static final String DEFAULT_CONFIGURATION_DIRECTORY = "./META-INF/";

    /**
     * Under this key could be found value in application resources with
     * annotation @Symbol
     */
    public static final String CONF_KEY_CONFIGURATION_DIRECTORY = "system.property.configuration.directory";

    /**
     * Get valid server role
     * 
     * @return server role
     */
    String getServerRole();

    /**
     * Method that get directory where is stored application configuration.
     * Currently file <code>configuration-...xml</code> and
     * <code>log4j-...xml</code>.
     * 
     * @return path to directory where application configuration is stored
     */
    String getConfigurationDirectory();

    /**
     * Return configuration file called 'configuration-&lt;server.role&gt;.xml'.
     * 
     * @return default application configuration file
     */
    Resource getDefaultConfiguration();

    /**
     * Return configuration file called
     * '&lt;configurationName&gt;-&lt;server.role&gt;.xml'.
     * 
     * @param configurationName
     *            required configuration name, name have to be usable as part of
     *            file name
     * @return application configuration file
     */
    Resource getConfiguration(String configurationName);

    /**
     * Return configuration file called
     * '&lt;configurationName&gt;-&lt;server.role&gt;.&lt;fileExtension&gt;'.
     * 
     * @param configurationName
     *            required configuration name, name have to be usable as part of
     *            file name
     * @param fileExtension
     *            required file name extension without leading dot
     * @return application configuration file
     */
    Resource getConfiguration(String configurationName, String fileExtension);

    /**
     * Creates {@link Properties}
     * 
     * @param configurationSection
     *            pattern that matches an element in configuration
     * @return properties loaded from XML configuration file, no file cannot be
     *         found
     */
    Map<String, String> loadDefaultConfiguration(String configurationSection);

    String getProperty(String key);

    Integer getPropertyInt(String key);
}
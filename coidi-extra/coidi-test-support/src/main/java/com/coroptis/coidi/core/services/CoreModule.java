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

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ApplicationInitializerFilter;

import com.coroptis.coidi.core.services.impl.AppSymbolProvider;
import com.coroptis.coidi.core.services.impl.ConfServiceImpl;
import com.coroptis.coidi.core.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptoSessionServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptographyServiceImpl;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;
import com.coroptis.coidi.core.util.Conf;

/**
 * This tapestry module helps to initialize T% in a same way in a different
 * application.
 * 
 * @author jan
 * 
 */
public class CoreModule {

    private final static Logger logger = Logger.getLogger(CoreModule.class);

    public static void bind(ServiceBinder binder) {
	binder.bind(ConfService.class, ConfServiceImpl.class);
	binder.bind(SymbolProvider.class, AppSymbolProvider.class).withId("appSymbolProvider");
	binder.bind(MessageService.class, MessageServiceImpl.class);
	binder.bind(CryptographyService.class, CryptographyServiceImpl.class);
	binder.bind(CryptoSessionService.class, CryptoSessionServiceImpl.class);
	binder.bind(SigningService.class, SigningServiceImpl.class);
	binder.bind(ConvertorService.class, ConvertorServiceImpl.class);
    }

    public static void contributeSymbolSource(final OrderedConfiguration<SymbolProvider> providers,
	    @InjectService("appSymbolProvider") SymbolProvider appSymbolProvider) {
	providers.add("appSymbolProvider", appSymbolProvider, "after:factoryDefaults");
    }

    public static void contributeApplicationInitializer(
	    OrderedConfiguration<ApplicationInitializerFilter> configuration,
	    @Inject @Symbol(ConfService.CONF_KEY_CONFIGURATION_DIRECTORY) final String systemPropertyConfigurationDirectory) {
	String configFile = Conf.getConfigurationDirectory(systemPropertyConfigurationDirectory)
		+ "log4j-" + Conf.getServerRole() + ".xml";
	initLog4jFromConfigXml(configFile);
	logger.debug("log4j system was configured");
    }

    /**
     * Initialize log4j using other than log4j.xml file.
     * <p>
     * This method shoudn't be called mode than once.
     * </p>
     * 
     * @param log4jConfigFileName
     *            filename such as "log4j-local.xml"
     */
    private final static void initLog4jFromConfigXml(String log4jConfigFileName) {
	/**
	 * Disable productions log4j configuration
	 */
	BasicConfigurator.resetConfiguration();
	/**
	 * Force log4j to load configuration special for tests
	 */
	if (log4jConfigFileName.startsWith(".")) {
	    /**
	     * Relative path
	     */
	    URL url = CoreModule.class.getClassLoader().getResource(log4jConfigFileName);
	    if (url == null)
		throw new NullPointerException("url '" + log4jConfigFileName + "' is null");
	    DOMConfigurator.configure(url);
	} else {
	    /**
	     * Absolute path
	     */
	    DOMConfigurator.configure(log4jConfigFileName);
	}
	logger.debug("log4j is loaded from file: " + log4jConfigFileName);
    }

}

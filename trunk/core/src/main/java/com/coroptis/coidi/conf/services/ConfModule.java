package com.coroptis.coidi.conf.services;

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

import com.coroptis.coidi.conf.services.impl.AppSymbolProvider;
import com.coroptis.coidi.conf.services.impl.ConfigurationServiceImpl;
import com.coroptis.coidi.conf.services.impl.CryptographyServiceImpl;
import com.coroptis.coidi.conf.services.impl.MessageServiceImpl;
import com.coroptis.coidi.conf.util.Conf;

/**
 * This tapestry module helps to initialize T% in a same way in a different
 * application.
 * 
 * @author jan
 * 
 */
public class ConfModule {

	private final static Logger logger = Logger.getLogger(ConfModule.class);

	public static void bind(ServiceBinder binder) {
		binder.bind(ConfigurationService.class, ConfigurationServiceImpl.class)
				.eagerLoad();
		binder.bind(SymbolProvider.class, AppSymbolProvider.class).withId(
				"appSymbolProvider");
		binder.bind(MessageService.class, MessageServiceImpl.class);
		binder.bind(CryptographyService.class, CryptographyServiceImpl.class);
	}

	public static void contributeSymbolSource(
			final OrderedConfiguration<SymbolProvider> providers,
			@InjectService("appSymbolProvider") SymbolProvider appSymbolProvider) {
		providers.add("appSymbolProvider", appSymbolProvider,
				"after:factoryDefaults");
	}

	public static void contributeApplicationInitializer(
			OrderedConfiguration<ApplicationInitializerFilter> configuration,
			@Inject @Symbol(ConfigurationService.CONF_KEY_CONFIGURATION_DIRECTORY) final String systemPropertyConfigurationDirectory) {
		String configFile = Conf
				.getConfigurationDirectory(systemPropertyConfigurationDirectory)
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
			URL url = ConfModule.class.getClassLoader().getResource(
					log4jConfigFileName);
			if (url == null)
				throw new NullPointerException("url '" + log4jConfigFileName
						+ "' is null");
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

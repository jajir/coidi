package com.coroptis.coidi.core.util;

import java.io.File;

import com.coroptis.coidi.core.services.ConfigurationService;

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
	 * @return
	 */
	public static String getConfigurationDirectory(
			final String systemPropertyConfigurationDirectory) {
		String confDir = System
				.getProperty(systemPropertyConfigurationDirectory);
		if (confDir == null || confDir.length() == 0) {
			return ConfigurationService.DEFAULT_CONFIGURATION_DIRECTORY;
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
		String value = System
				.getProperty(ConfigurationService.SYSTEM_PROPERTY_SERVER_ROLE);
		if (value != null) {
			return value;
		}
		return ConfigurationService.DEFAULT_SERVER_ROLE;
	}

}

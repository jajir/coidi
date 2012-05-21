package com.coroptis.coidi.core.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.xml.sax.SAXException;

import com.coroptis.coidi.core.services.ConfException;
import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.util.Conf;
import com.coroptis.coidi.core.util.FsResource;

/**
 * Loads {@link Properties} from configuration file. Configuration files are
 * located in folder specified in system property "sso.conf.dir". If such a
 * property is not found then configuration files are searched for in
 * <code>src/main/resources/META-INF/</code> folder.<br/>
 * Files are named like this: <code>configuration-${server.role}.xml</code>
 * where ${server.role} is replaced by value of system property "server.role".
 * 
 * @author jan
 */
public class ConfigurationServiceImpl implements ConfigurationService {

	private final static Logger logger = Logger
			.getLogger(ConfigurationServiceImpl.class);

	private final String systemPropertyConfigurationDirectory;

	public ConfigurationServiceImpl(
			@Inject @Symbol(CONF_KEY_CONFIGURATION_DIRECTORY) final String systemPropertyConfigurationDirectory) {
		this.systemPropertyConfigurationDirectory = systemPropertyConfigurationDirectory;
	}

	private Resource getConfigurationFileResource(final String configurationUrl) {
		Resource configurationResource = null;
		if (configurationUrl.startsWith(".")) {
			/**
			 * Relative path
			 */
			configurationResource = new ClasspathResource(configurationUrl);
		} else {
			/**
			 * Absolute path
			 */
			configurationResource = new FsResource(configurationUrl);
		}
		if (!configurationResource.exists()) {
			throw new ConfException("Configuration file '" + configurationUrl
					+ "' didn't exists.");
		}
		return configurationResource;
	}

	/**
	 * Creates {@link Properties} instance. Properties are read from the
	 * specified XML file and XML elements.
	 * 
	 * @param configurationSection
	 *            pattern that matches an element in configuration
	 * @param resourceUrl
	 *            specifies where configuration file is located
	 * @return Map loaded from XML configuration file
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap<String, String> createProperties(
			String configurationSection, Resource configurationResource) {
		Digester digester = new Digester();
		digester.addObjectCreate("configuration/" + configurationSection,
				HashMap.class);
		//TODO refactor it, add test
		// call the put method on the top object on the digester stack
		// passing the key attribute as the 0th parameterw
		// and the element body text as the 1th parameter..
		digester.addCallMethod("configuration/" + configurationSection
				+ "/property", "put", 2);
		digester.addCallParam("configuration/" + configurationSection
				+ "/property", 0, "name");
		digester.addCallParam("configuration/" + configurationSection
				+ "/property", 1);

		InputStream inputStream = null;
		HashMap properties = null;
		try {
			inputStream = configurationResource.openStream();
			properties = (HashMap) digester.parse(inputStream);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.warn("Unable to close input stream after reading configuration file.");
			}
		}
		return properties;
	}

	/**
	 * @return path on classpath where the configuration file is located.
	 */
	private String getDefaultConfigurationFile() {
		return getConfigurationDirectory() + "configuration-" + getServerRole()
				+ ".xml";
	}

	/**
	 * @return Value of server.role property or it's default value if not
	 *         defined.
	 */
	@Override
	public String getServerRole() {
		String value = System.getProperty(SYSTEM_PROPERTY_SERVER_ROLE);
		if (value != null) {
			return value;
		}
		return DEFAULT_SERVER_ROLE;
	}

	@Override
	public String getConfigurationDirectory() {
		return Conf
				.getConfigurationDirectory(systemPropertyConfigurationDirectory);
	}

	@Override
	public Resource getDefaultConfiguration() {
		return getConfigurationFileResource(getDefaultConfigurationFile());
	}

	@Override
	public Map<String, String> loadDefaultConfiguration(
			String configurationSection) {
		Resource configurationResource = getDefaultConfiguration();
		return createProperties(configurationSection, configurationResource);
	}

	@Override
	public String getProperty(String symbolName) {
		int pos = symbolName.indexOf(".");
		if (pos < 0) {
			return null;
		}
		String section = symbolName.substring(0, pos);
		String key = symbolName.substring(pos + 1);
		Map<String, String> prop = loadDefaultConfiguration(section);
		if (prop == null) {
			return null;
		}
		return prop.get(key);
	}

	@Override
	public Integer getPropertyInt(String symbolName) {
		String val = getProperty(symbolName);
		return val == null ? null : Integer.parseInt(val);
	}

}

package com.coroptis.coidi.integration.op.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Load property file 'application.properties' and bind all contained properties
 * to module. Property key will by name under which will be property available
 * in guice.
 * 
 * @author honza
 * 
 */
public class PropertyModule extends AbstractModule {

	private final Logger logger = LoggerFactory.getLogger(PropertyModule.class);

	@Override
	protected void configure() {
		bindApplicationProperties();
	}

	private void bindApplicationProperties() {
		try {
			final Properties properties = new Properties();
			properties
					.load(new FileReader(getClass().getClassLoader().getResource("application.properties").getFile()));
			for (final String name : properties.stringPropertyNames()) {
				final String value = properties.getProperty(name);
				logger.debug("setting value '{}' to key '{}'", value, name);
				if (isInteger(value)) {
					bind(Integer.class).annotatedWith(Names.named(name)).toInstance(Integer.parseInt(value));
				} else {
					bind(String.class).annotatedWith(Names.named(name)).toInstance(value);
				}
			}
		} catch (IOException e) {
			throw new CoidiException(e.getMessage(), e);
		}
	}

	private boolean isInteger(final String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}

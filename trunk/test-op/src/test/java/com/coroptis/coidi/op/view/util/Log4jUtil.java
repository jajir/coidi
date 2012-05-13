package com.coroptis.coidi.op.view.util;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Help properly initialize log4j in test environment. JUnit tests should be
 * extended from this class.
 * 
 * @author jan
 * 
 */
public class Log4jUtil {

	protected final static Logger logger = Logger.getLogger(Log4jUtil.class);

	/**
	 * Disable production log4j.xml configure file and load test log4j
	 * configuration.
	 * <p>
	 * This method shoudn't be called mode than once.
	 * </p>
	 */
	public final static void initLog4j() {
		/**
		 * Disable productions log4j configuration
		 */
		BasicConfigurator.resetConfiguration();
		/**
		 * Force log4j to load configuration special for tests
		 */
		URL url = Log4jUtil.class.getClassLoader()
				.getResource("log4j-test.xml");
		if (url == null)
			throw new NullPointerException("url is null");
		System.out.println(url.toString());
		DOMConfigurator.configure(url);
		logger.debug("log4j is up");
	}

}

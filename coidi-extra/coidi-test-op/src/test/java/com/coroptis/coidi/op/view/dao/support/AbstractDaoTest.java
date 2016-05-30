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
package com.coroptis.coidi.op.view.dao.support;

import java.io.FileInputStream;
import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.log4j.Logger;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.test.PageTester;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;

import com.coroptis.coidi.core.services.CoreModule;
import com.coroptis.coidi.op.view.services.OpViewModule;
import com.google.common.base.Preconditions;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import mx4j.tools.config.ConfigurationException;

/**
 * All DAO test should be extended from this class. It provide secure datasets
 * loading.
 * 
 * 
 * @author jan
 * 
 */
public abstract class AbstractDaoTest extends DatabaseTestCase {

	public static final String MOCK_DATA_PATH = "src/test/mock-data/";

	protected static final Logger logger = Logger.getLogger(AbstractDaoTest.class);

	protected static final String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.op.view";

	protected static final String T5_APPLICATION_NAME = "OpView";

	protected static final String T5_WEBAPP_BASE = "src/main/webapp";

	static {
		System.setProperty("server.role", "junit");
		System.setProperty("system.property.configuration.directory", "non-existing");

		try {
			logger.debug("Starting Dery and liquibase");
			EmbeddedDataSource dataSource = createDataSource();
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
			Liquibase liquibase = new Liquibase("db/db-tables1.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update(new Contexts(), new LabelExpression());
			bindToJNDI("jdbc:derby:memory:coidi_op;create=true", createDataSource());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.debug("loading T5 registry with server.role.junit: " + System.getProperty("server.role"));
		pageTester = new PageTester(T5_APPLICATION_PACKAGE, T5_APPLICATION_NAME, T5_WEBAPP_BASE, CoreModule.class,
				OpViewModule.class);
	}

	public static EmbeddedDataSource createDataSource() {
		EmbeddedDataSource dataSource = new EmbeddedDataSource();
		dataSource.setDataSourceName("jdbc:derby:memory:coidi_op;create=true");
		dataSource.setDatabaseName("memory:coidi_op");
		dataSource.setCreateDatabase("create");

		return dataSource;

	}

	public static void bindToJNDI(String propertyName, Object value) throws ConfigurationException {
		try {
			Context ctx = new InitialContext();
			ctx.bind(propertyName, value);
		} catch (NamingException e) {
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Basic T5 object allowing to start web application in testing mode and
	 * then access pages, clink on links and post forms.
	 */
	private static PageTester pageTester;

	/**
	 * Get connections to real database.
	 * 
	 * @return connection to database
	 * @throws Exception
	 */
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		Preconditions.checkNotNull(pageTester.getRegistry(), "registry");
		Session session = pageTester.getRegistry().getService(Session.class);
		IDatabaseConnection out = new MockIDatabaseConnection(session.getSessionFactory());
		DatabaseConfig config = out.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
		return out;
	}

	/**
	 * Get connection to fake data. When test method {@link #getDatasetsFiles()}
	 * return null then file from system property
	 * <code>dataset.file.location</code> are used.
	 * 
	 * @return initialized dataset
	 * @throws DataSetException
	 * @throws IOException
	 */
	@Override
	protected IDataSet getDataSet() throws DataSetException, IOException {
		return new FlatXmlDataSet(new FileInputStream(MOCK_DATA_PATH + "basic.xml"));
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	protected void commit() {
		HibernateSessionManager hibernateSessionManager = getService(HibernateSessionManager.class);
		Session session = hibernateSessionManager.getSession();
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		/**
		 * This is done when T5 are colled from view.
		 */
		PerthreadManager perthreadManager = getService(PerthreadManager.class);
		perthreadManager.cleanup();
		super.tearDown();
	}

	protected <T> T getService(Class<T> serviceInterface) {
		return pageTester.getRegistry().getService(serviceInterface);
	}
}

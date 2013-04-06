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
package com.coroptis.coidi.op.view.util;

import java.io.FileInputStream;
import java.io.IOException;

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
import com.coroptis.coidi.test.MockIDatabaseConnection;
import com.google.common.base.Preconditions;

/**
 * All DAO test should be extended from this class. It provide secure datasets
 * loading.
 * 
 * 
 * @author jan
 * 
 */
public abstract class AbstractDaoTest extends DatabaseTestCase {

    public final static String MOCK_DATA_PATH = "src/test/mock-data/";

    protected final static Logger logger = Logger.getLogger(AbstractDaoTest.class);

    protected final static String T5_APPLICATION_PACKAGE = "com.coroptis.coidi.op.view";

    protected final static String T5_APPLICATION_NAME = "OpView";

    protected final static String T5_WEBAPP_BASE = "src/main/webapp";

    static {
	System.setProperty("server.role", "junit");
	System.setProperty("system.property.configuration.directory", "non-existing");
	logger.debug("loading T5 registry with server.role.junit: "
		+ System.getProperty("server.role"));
	pageTester = new PageTester(T5_APPLICATION_PACKAGE, T5_APPLICATION_NAME, T5_WEBAPP_BASE,
		CoreModule.class, OpViewModule.class);
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

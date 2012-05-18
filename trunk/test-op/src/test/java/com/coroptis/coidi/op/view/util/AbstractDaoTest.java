package com.coroptis.coidi.op.view.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.tapestry5.hibernate.HibernateCoreModule;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.services.TapestryModule;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;

import com.coroptis.coidi.core.services.CoreModule;

/**
 * All DAO test should be extended from this class. It provide secure datasets
 * loading.
 * 
 * TODO mock database connection should be aligned with JDBC connection as 1:1
 * 
 * FIXME test doesn't work, recursive reference in configuration module
 * 
 * @author jan
 * 
 */
public abstract class AbstractDaoTest extends DatabaseTestCase {

	public final static String MOCK_DATA_PATH = "src/test/mock-data/";

	protected final static Logger logger = Logger
			.getLogger(AbstractDaoTest.class);

	protected static Registry registry;

	static {
		System.setProperty("server.role", "junit");
		System.setProperty("op.fill.with.data.on.startup", "false");
		RegistryBuilder builder = new RegistryBuilder();
		builder.add(TapestryModule.class);
		builder.add(DaoTestAppModule.class);
		builder.add(HibernateCoreModule.class);
		builder.add(CoreModule.class);
		Registry registry = builder.build();
		registry.performRegistryStartup();
	}

	/**
	 * Get connections to real database.
	 * 
	 * @return connection to database
	 * @throws Exception
	 */
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		Session session = registry.getService(Session.class);
		return new DatabaseConnection(session.connection());
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
		return new FlatXmlDataSet(new FileInputStream(MOCK_DATA_PATH
				+ "basic.xml"));
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected <T> T getService(Class<T> serviceInterface) {
		return registry.getService(serviceInterface);
	}
}

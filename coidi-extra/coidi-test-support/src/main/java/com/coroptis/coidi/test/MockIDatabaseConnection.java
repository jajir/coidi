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
package com.coroptis.coidi.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.statement.IStatementFactory;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

/**
 * It delegates all operations to some other database connection. And solve
 * problem with closed lived connection.
 * <p>
 * Dbunit hold own connection to database during test execution even if it's not
 * needed. This delegating class allows to close connection to session after
 * setUp and after tearDown methods.
 * </p>
 * 
 * @author jan
 * 
 */
public class MockIDatabaseConnection implements IDatabaseConnection {

	private IDatabaseConnection databaseConnection;

	private Session session;

	private final SessionFactory sessionFactory;

	private final Logger logger = Logger
			.getLogger(MockIDatabaseConnection.class);

	public MockIDatabaseConnection(final SessionFactory sessionFactory) {
		// Defend.notNull(sessionFactory, "sessionFactory is null");
		Preconditions.checkNotNull(sessionFactory, "sessionFactory");
		this.sessionFactory = sessionFactory;
	}

	private IDatabaseConnection getIDatabaseConnection() {
		if (databaseConnection == null) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				databaseConnection = new DatabaseConnection(session
						.connection());
			} catch (HibernateException e) {
				logger.error(e.getMessage(), e);
				throw new IllegalArgumentException(e.getMessage(), e);
			} catch (DatabaseUnitException e) {
				logger.error(e.getMessage(), e);
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
		return databaseConnection;
	}

	public void closeConnection()  {
		if (session != null) {
			if (session.getTransaction() != null) {
				session.getTransaction().commit();
			}
			session.close();
			session = null;
			databaseConnection = null;
		}
	}

	@Override
	public void close() throws SQLException {
		closeConnection();
	}

	@Override
	public IDataSet createDataSet() throws SQLException {
		return getIDatabaseConnection().createDataSet();
	}

	@Override
	public IDataSet createDataSet(String[] tableNames) throws SQLException {
		return getIDatabaseConnection().createDataSet(tableNames);
	}

	@Override
	public ITable createQueryTable(String tableName, String sql)
			throws DataSetException, SQLException {
		return getIDatabaseConnection().createQueryTable(tableName, sql);
	}

	@Override
	public DatabaseConfig getConfig() {
		return getIDatabaseConnection().getConfig();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getIDatabaseConnection().getConnection();
	}

	@Override
	public int getRowCount(String tableName) throws SQLException {
		return getIDatabaseConnection().getRowCount(tableName);
	}

	@Override
	public int getRowCount(String tableName, String whereClause)
			throws SQLException {
		return getIDatabaseConnection().getRowCount(tableName, whereClause);
	}

	@Override
	public String getSchema() {
		return getIDatabaseConnection().getSchema();
	}

	@Override
	@SuppressWarnings("deprecation")
	public IStatementFactory getStatementFactory() {
		return getIDatabaseConnection().getStatementFactory();
	}

}

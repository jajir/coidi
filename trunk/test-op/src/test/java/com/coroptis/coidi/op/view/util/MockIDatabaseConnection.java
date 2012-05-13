package com.coroptis.coidi.op.view.util;

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

	public void closeConnection() throws SQLException {
		if (session != null) {
			if (session.getTransaction() != null) {
				session.getTransaction().commit();
			}
			session.close();
			session = null;
			databaseConnection = null;
		}
	}

	public void close() throws SQLException {
		closeConnection();
	}

	public IDataSet createDataSet() throws SQLException {
		return getIDatabaseConnection().createDataSet();
	}

	public IDataSet createDataSet(String[] tableNames) throws SQLException {
		return getIDatabaseConnection().createDataSet(tableNames);
	}

	public ITable createQueryTable(String tableName, String sql)
			throws DataSetException, SQLException {
		return getIDatabaseConnection().createQueryTable(tableName, sql);
	}

	public DatabaseConfig getConfig() {
		return getIDatabaseConnection().getConfig();
	}

	public Connection getConnection() throws SQLException {
		return getIDatabaseConnection().getConnection();
	}

	public int getRowCount(String tableName) throws SQLException {
		return getIDatabaseConnection().getRowCount(tableName);
	}

	public int getRowCount(String tableName, String whereClause)
			throws SQLException {
		return getIDatabaseConnection().getRowCount(tableName, whereClause);
	}

	public String getSchema() {
		return getIDatabaseConnection().getSchema();
	}

	@SuppressWarnings("deprecation")
	public IStatementFactory getStatementFactory() {
		return getIDatabaseConnection().getStatementFactory();
	}

}

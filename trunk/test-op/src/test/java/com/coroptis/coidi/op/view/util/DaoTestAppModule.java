package com.coroptis.coidi.op.view.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.IdentityService;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.view.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.view.services.impl.UserServiceImpl;
import com.google.common.io.Files;

/**
 * Testing application module. This Testing module load DAO application layer.
 * Database configuration and initialization isn't perform here. It have to be
 * done outside of this module.
 * <p>
 * Can be used for DAO testing.
 * </p>
 * 
 * @author jan
 * 
 */
public class DaoTestAppModule {

	private final static Logger logger = Logger
			.getLogger(DaoTestAppModule.class);

	public static void bind(ServiceBinder binder) {
		logger.debug("Starting test app module ....");
		binder.bind(UserService.class, UserServiceImpl.class);
		binder.bind(IdentityService.class, IdentityServiceImpl.class);
		binder.bind(AssociationService.class, AssociationServiceImpl.class);
	}

	@Startup
	public static void initDatabase(
			final @Inject @Symbol("op.fill.with.data.on.startup") Boolean initDatabase,
			final @Inject @Symbol("op.init.sql.data") String initSqlFile,
			final @InjectService("session") Session session) throws IOException {
		if (initDatabase) {
			for (final String line : Files.readLines(new File(initSqlFile),
					Charset.forName("UTF-8"))) {
				logger.debug("executing: " + line);
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection)
							throws SQLException {
						connection.createStatement().execute(line);
						connection.commit();
					}
				});
			}
		}
	}

}

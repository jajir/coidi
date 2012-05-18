package com.coroptis.coidi.op.view.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.Dispatcher;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.coroptis.coidi.op.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.op.view.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.view.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.view.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.view.services.impl.NonceServiceImpl;
import com.coroptis.coidi.op.view.services.impl.OpenIdDispatcherAssociation;
import com.coroptis.coidi.op.view.services.impl.OpenIdDispatcherChecker;
import com.coroptis.coidi.op.view.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.view.services.impl.OpenidDispatcherAuthentication;
import com.coroptis.coidi.op.view.services.impl.UserServiceImpl;
import com.coroptis.coidi.op.view.services.impl.XrdsServiceImpl;
import com.google.common.io.Files;

public class OpViewModule {

	private final static Logger logger = Logger.getLogger(OpViewModule.class);

	public static void bind(ServiceBinder binder) {
		binder.bind(XrdsService.class, XrdsServiceImpl.class);
		binder.bind(UserService.class, UserServiceImpl.class);
		binder.bind(IdentityService.class, IdentityServiceImpl.class);
		binder.bind(AssociationService.class, AssociationServiceImpl.class);
		binder.bind(CryptoService.class, CryptoServiceImpl.class);
		binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
				"accessControllerDispatcher");
		binder.bind(NonceService.class, NonceServiceImpl.class);
	}

	@Startup
	public static void initDatabase(
			final @Inject @Symbol("op.fill.with.data.on.startup") Boolean initDatabase,
			final @Inject @Symbol("op.init.sql.data") String initSqlFile,
			final @InjectService("session") Session session) throws IOException {
		if (initDatabase) {
			for (final String line : Files.readLines(new File(initSqlFile),
					Charset.forName("UTF-8"))) {
				if (line.length() > 0) {
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

	public static void contributeMasterDispatcher(
			OrderedConfiguration<Dispatcher> configuration,
			@InjectService("accessControllerDispatcher") Dispatcher accessController) {
		configuration.add("accessControllerDispatcher", accessController,
				"before:PageRender");
	}

	public static OpenIdDispatcher buildOpenIdDispatcher(
			List<OpenIdDispatcher> commands,
			@InjectService("ChainBuilder") ChainBuilder chainBuilder) {
		return chainBuilder.build(OpenIdDispatcher.class, commands);
	}

	public static void contributeOpenIdDispatcher(
			OrderedConfiguration<OpenIdDispatcher> configuration,
			@Autobuild OpenIdDispatcherChecker openIdDispatcherChecker,
			@Autobuild OpenidDispatcherAuthentication openidDispatcherAuthentication,
			@Autobuild OpenIdDispatcherAssociation openIdDispatcherAssociation,
			@Autobuild OpenIdDispatcherTerminator openIdDispatcherTerminator) {
		configuration.add("openIdDispatcherChecker", openIdDispatcherChecker);
		configuration.add("openidDispatcherAuthentication",
				openidDispatcherAuthentication);
		configuration.add("openIdDispatcherAssociation",
				openIdDispatcherAssociation);
		configuration.add("openIdDispatcherTerminator",
				openIdDispatcherTerminator);
	}

	@Match("*Service")
	public static void adviseTransactions(HibernateTransactionAdvisor advisor,
			MethodAdviceReceiver receiver) {
		advisor.addTransactionCommitAdvice(receiver);
	}
}

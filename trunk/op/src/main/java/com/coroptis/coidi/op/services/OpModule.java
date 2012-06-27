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
package com.coroptis.coidi.op.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.hibernate.HibernateConfigurer;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.Dispatcher;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.dao.UserDao;
import com.coroptis.coidi.op.dao.impl.AssociationDaoImpl;
import com.coroptis.coidi.op.dao.impl.IdentityDaoImpl;
import com.coroptis.coidi.op.dao.impl.StatelessModeNonceDaoImpl;
import com.coroptis.coidi.op.dao.impl.UserDaoImpl;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationSetup;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;
import com.coroptis.coidi.op.services.impl.UserServiceImpl;
import com.coroptis.coidi.op.services.impl.XrdsServiceImpl;
import com.google.common.io.Files;

public class OpModule {// NO_UCD

	private final static Logger logger = Logger.getLogger(OpModule.class);

	public static void bind(ServiceBinder binder) {
		/**
		 * DAO
		 */
		binder.bind(StatelessModeNonceDao.class,
				StatelessModeNonceDaoImpl.class);
		binder.bind(AssociationDao.class, AssociationDaoImpl.class);
		binder.bind(IdentityDao.class, IdentityDaoImpl.class);
		binder.bind(UserDao.class, UserDaoImpl.class);

		/**
		 * Services
		 */
		binder.bind(XrdsService.class, XrdsServiceImpl.class);
		binder.bind(IdentityService.class, IdentityServiceImpl.class);
		binder.bind(AssociationService.class, AssociationServiceImpl.class);
		binder.bind(CryptoService.class, CryptoServiceImpl.class);
		binder.bind(UserService.class, UserServiceImpl.class);
		binder.bind(AuthenticationService.class,
				AuthenticationServiceImpl.class);
		binder.bind(StatelessModeNonceService.class,
				StatelessModeNonceServiceImpl.class);
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
			@Autobuild OpenidDispatcherAuthenticationImmediate openidDispatcherAuthenticationImmediate,
			@Autobuild OpenidDispatcherAuthenticationSetup openidDispatcherAuthenticationSetup,
			@Autobuild OpenIdDispatcherCheckAuthentication openIdDispatcherCheckAuthentication,
			@Autobuild OpenIdDispatcherAssociation openIdDispatcherAssociation,
			@Autobuild OpenIdDispatcherTerminator openIdDispatcherTerminator) {
		configuration.add("openIdDispatcherChecker", openIdDispatcherChecker);
		configuration.add("openidDispatcherAuthenticationImmediate",
				openidDispatcherAuthenticationImmediate);
		configuration.add("openidDispatcherAuthenticationSetup",
				openidDispatcherAuthenticationSetup);
		configuration.add("openIdDispatcherAssociation",
				openIdDispatcherAssociation);
		configuration.add("openIdDispatcherCheckAuthentication",
				openIdDispatcherCheckAuthentication);
		configuration.add("openIdDispatcherTerminator",
				openIdDispatcherTerminator);
	}

	@Match("*Dao")
	public static void adviseDaoTransactions(
			HibernateTransactionAdvisor advisor, MethodAdviceReceiver receiver) {
		advisor.addTransactionCommitAdvice(receiver);
	}

	@Contribute(HibernateSessionSource.class)
	public static void loadHibernateConf(
			OrderedConfiguration<HibernateConfigurer> config,
			@Inject final ConfigurationService configurationService) {
		final Resource configFile = configurationService
				.getConfiguration("hibernate");
		logger.debug("Applying special hibernate conf from "
				+ configFile.getFile());
		config.override("Default", new HibernateConfigurer() {

			@Override
			public void configure(Configuration configuration) {
				configuration.configure(configFile.toURL());
			}
		});
	}

}

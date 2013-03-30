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
package com.coroptis.coidi.op.view.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateConfigurer;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.Dispatcher;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.dao.BaseUserDao;
import com.coroptis.coidi.op.view.dao.impl.BaseAssociationDaoImpl;
import com.coroptis.coidi.op.view.dao.impl.BaseIdentityDaoImpl;
import com.coroptis.coidi.op.view.dao.impl.BaseNonceDaoImpl;
import com.coroptis.coidi.op.view.dao.impl.BaseUserDaoImpl;
import com.coroptis.coidi.op.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.op.view.services.impl.IdentityGds;
import com.google.common.io.Files;

public class OpViewModule {// NO_UCD

    private final static Logger logger = Logger.getLogger(OpViewModule.class);

    public static void bind(ServiceBinder binder) {
	/**
	 * DAO
	 */
	binder.bind(BaseNonceDao.class, BaseNonceDaoImpl.class);
	binder.bind(BaseAssociationDao.class, BaseAssociationDaoImpl.class);
	binder.bind(BaseIdentityDao.class, BaseIdentityDaoImpl.class);
	binder.bind(BaseUserDao.class, BaseUserDaoImpl.class);

	binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
		"accessControllerDispatcher");
	binder.bind(GridDataSource.class, IdentityGds.class).withId("identityGds");
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
			public void execute(Connection connection) throws SQLException {
			    connection.createStatement().execute(line);
			    connection.commit();
			}
		    });
		}
	    }
	}
    }

    public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
	    @InjectService("accessControllerDispatcher") Dispatcher accessController) {
	configuration.add("accessControllerDispatcher", accessController, "before:PageRender");
    }

    public static void contributeHibernateEntityPackageManager(Configuration<String> conf) {
	conf.add("com.coroptis.coidi.op.view.entities");
    }

    @Match("*Dao")
    public static void adviseDaoTransactions(MethodAdviceReceiver receiver,
	    final HibernateSessionManager manager) {
	for (Method m : receiver.getInterface().getMethods()) {
	    if (m.getName().startsWith("register") || m.getName().startsWith("create")
		    || m.getName().startsWith("save") || m.getName().startsWith("update")
		    || m.getName().startsWith("delete")) {
		receiver.adviseMethod(m, new MethodAdvice() {
		    @Override
		    public void advise(MethodInvocation invocation) {
			try {
			    invocation.proceed();
			} catch (RuntimeException ex) {
			    manager.abort();
			    throw ex;
			}
			// For success or checked exception, commit the
			// transaction.
			manager.commit();
		    }
		});
	    }
	}
    }

    @Contribute(HibernateSessionSource.class)
    public static void loadHibernateConf(OrderedConfiguration<HibernateConfigurer> config,
	    @Inject final ConfigurationService configurationService) {
	final Resource configFile = configurationService.getConfiguration("hibernate");
	logger.debug("Applying special hibernate conf from " + configFile.getFile());
	config.override("Default", new HibernateConfigurer() {

	    @Override
	    public void configure(org.hibernate.cfg.Configuration configuration) {
		configuration.configure(configFile.toURL());
	    }
	});
    }

}

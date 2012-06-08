package com.coroptis.coidi.op.util;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.coroptis.coidi.test.AdditionalBinderProvider;
import com.coroptis.coidi.test.EasyMockServicebuilder;

public class JunitAppModule {

	public static void bind(ServiceBinder binder) {
		/**
		 * Additional bindings
		 */
		AdditionalBinderProvider.bind(binder);

		Services services = Services.getServices();

		binder.bind(
				ConfigurationService.class,
				new EasyMockServicebuilder<ConfigurationService>(services
						.getConfigurationService()));
		binder.bind(
				NonceService.class,
				new EasyMockServicebuilder<NonceService>(services
						.getNonceService()));
		binder.bind(
				AssociationDao.class,
				new EasyMockServicebuilder<AssociationDao>(services
						.getAssociationDao()));
		binder.bind(
				SigningService.class,
				new EasyMockServicebuilder<SigningService>(services
						.getSigningService()));
		binder.bind(
				StatelessModeNonceService.class,
				new EasyMockServicebuilder<StatelessModeNonceService>(services
						.getStatelessModeNonceService()));
	}
}

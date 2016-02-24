package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.rp.services.AssociationFactory;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;
import com.coroptis.coidi.rp.services.CoidiRp;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.RpService;
import com.google.common.base.Preconditions;

public class CoidiRpImpl implements CoidiRp {

	private final AssociationFactory associationFactory;

	private final DiscoveryProcessor discoveryProcessor;

	private final AuthenticationVerificationService authenticationVerificationService;

	private final MessageService messageService;

	private final RpService rpService;

	public CoidiRpImpl(final AssociationFactory associationFactory, final DiscoveryProcessor discoveryProcessor,
			final AuthenticationVerificationService authenticationVerificationService,
			final MessageService messageService, final RpService rpService) {
		this.associationFactory = Preconditions.checkNotNull(associationFactory);
		this.discoveryProcessor = Preconditions.checkNotNull(discoveryProcessor);
		this.authenticationVerificationService = Preconditions.checkNotNull(authenticationVerificationService);
		this.messageService = Preconditions.checkNotNull(messageService);
		this.rpService = Preconditions.checkNotNull(rpService);
	}

	@Override
	public AssociationFactory getAssociationFactory() {
		return associationFactory;
	}

	@Override
	public DiscoveryProcessor getDiscoveryProcessor() {
		return discoveryProcessor;
	}

	@Override
	public AuthenticationVerificationService getAuthenticationVerificationService() {
		return authenticationVerificationService;
	}

	@Override
	public MessageService getMessageService() {
		return messageService;
	}

	@Override
	public RpService getRpService() {
		return rpService;
	}

}

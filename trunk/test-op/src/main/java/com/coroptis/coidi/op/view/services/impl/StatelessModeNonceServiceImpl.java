package com.coroptis.coidi.op.view.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.view.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.view.entities.StatelessModeNonce;
import com.coroptis.coidi.op.view.services.CryptoService;
import com.coroptis.coidi.op.view.services.StatelessModeNonceService;

public class StatelessModeNonceServiceImpl implements StatelessModeNonceService {

	private final Logger logger;

	@Inject
	private ConvertorService convertorService;

	@Inject
	private CryptoService cryptoService;

	@Inject
	private StatelessModeNonceDao statelessModeNonceDao;

	@Inject
	private SigningService signingService;

	private final AssociationType statelesModeAssociationType;

	public StatelessModeNonceServiceImpl(
			// NO_UCD
			@Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
			final Logger logger) {
		this.logger = logger;
		statelesModeAssociationType = AssociationType.convert(assocTypeStr);
		logger.debug("Association type for stateless mode: "
				+ statelesModeAssociationType);
	}

	@Override
	public StatelessModeNonce createStatelessModeNonce(final String nonce) {
		StatelessModeNonce statelessModeNonce = new StatelessModeNonce();
		statelessModeNonce.setNonce(nonce);
		statelessModeNonce
				.setMacKey(convertorService.convertToString(cryptoService
						.generateAssociationRandom(statelesModeAssociationType)));
		logger.debug("Creating stateless nonce: " + statelessModeNonce);
		statelessModeNonceDao.save(statelessModeNonce);
		return statelessModeNonce;
	}

	@Override
	public StatelessModeNonce getByNonce(final String noce) {
		return statelessModeNonceDao.getByNonce(noce);
	}

	@Override
	public Boolean verifyCheckAuthenticationRequest(
			final CheckAuthenticationRequest request) {
		StatelessModeNonce statelessModeNonce = getByNonce(request.getNonce());
		String signature = signingService.sign(request,
				statelessModeNonce.getMacKey());
		if (signature.equals(request.getSignature())) {
			return true;
		} else {
			logger.info("Signature from check_authentication message '"
					+ request.getSignature()
					+ "' is not same as computed one '" + signature + "'");
			return false;
		}
	}
}

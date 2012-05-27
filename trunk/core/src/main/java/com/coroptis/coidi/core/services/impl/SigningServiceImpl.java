package com.coroptis.coidi.core.services.impl;

import java.io.UnsupportedEncodingException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;

public class SigningServiceImpl implements SigningService {

	@Inject
	private Logger logger;

	@Inject
	private CryptographyService cryptoService;

	@Inject
	private MessageService messageService;

	@Inject
	private ConvertorService convertorService;

	@Override
	public String sign(final AbstractMessage response,
			final Association association) {
		String toSign = messageService
				.extractStringForSign(response, "openid.");
		try {
			byte[] b = cryptoService.hmacSha1(
					convertorService.convertToBytes(association.getMacKey()),
					toSign.getBytes("UTF-8"), association.getAssociationType());
			return convertorService.convertToString(b);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

	@Override
	public String sign(final AbstractMessage response, final String macKey,
			final AssociationType associationType) {
		String toSign = messageService
				.extractStringForSign(response, "openid.");
		try {
			byte[] b = cryptoService.hmacSha1(
					convertorService.convertToBytes(macKey),
					toSign.getBytes("UTF-8"), associationType);
			return convertorService.convertToString(b);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

}

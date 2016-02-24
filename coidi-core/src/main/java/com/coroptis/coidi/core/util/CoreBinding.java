package com.coroptis.coidi.core.util;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.core.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptoSessionServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptographyServiceImpl;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;
import com.coroptis.coidi.core.services.impl.NonceServiceImpl;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;

/**
 * Initialize coidi core classes.
 * 
 * @author jiroutj
 *
 */
public class CoreBinding extends AbstractBinding {

	/**
	 * {@link ConvertorService}
	 */
	private Lazy<ConvertorService> convertorService = new Lazy<ConvertorService>(initConvertorService());

	public final ConvertorService getConvertorService() {
		return convertorService.get();
	}

	protected Init<ConvertorService> initConvertorService() {
		return new Init<ConvertorService>() {
			@Override
			public ConvertorService create() {
				return new ConvertorServiceImpl();
			}
		};
	}

	/**
	 * {@link NonceService}
	 */
	private Lazy<NonceService> nonceService = new Lazy<NonceService>(initNonceService());

	public final NonceService getNonceService() {
		return nonceService.get();
	}

	protected Init<NonceService> initNonceService() {
		return new Init<NonceService>() {
			@Override
			public NonceService create() {
				return new NonceServiceImpl(getConvertorService());
			}
		};
	}

	/**
	 * {@link MessageService}
	 */
	private Lazy<MessageService> messageService = new Lazy<MessageService>(initMessageService());

	public final MessageService getMessageService() {
		return messageService.get();
	}

	protected Init<MessageService> initMessageService() {
		return new Init<MessageService>() {
			@Override
			public MessageService create() {
				return new MessageServiceImpl();
			}
		};
	}

	/**
	 * {@link CryptographyService}
	 */
	private Lazy<CryptographyService> cryptographyService = new Lazy<CryptographyService>(initCryptographyService());

	public final CryptographyService getCryptographyService() {
		return cryptographyService.get();
	}

	protected Init<CryptographyService> initCryptographyService() {
		return new Init<CryptographyService>() {
			@Override
			public CryptographyService create() {
				return new CryptographyServiceImpl();
			}
		};
	}

	/**
	 * {@link CryptoSessionService}
	 */
	private Lazy<CryptoSessionService> cryptoSessionService = new Lazy<CryptoSessionService>(initCryptoSessionService());

	public final CryptoSessionService getCryptoSessionService() {
		return cryptoSessionService.get();
	}

	protected Init<CryptoSessionService> initCryptoSessionService() {
		return new Init<CryptoSessionService>() {
			@Override	
			public CryptoSessionService create() {
				return new CryptoSessionServiceImpl(getCryptographyService());
			}
		};
	}

	/**
	 * {@link SigningService}
	 */
	private Lazy<SigningService> signingService = new Lazy<SigningService>(initSigningService());

	public final SigningService getSigningService() {
		return signingService.get();
	}

	protected Init<SigningService> initSigningService() {
		return new Init<SigningService>() {
			@Override	
			public SigningService create() {
				return new SigningServiceImpl(getCryptographyService(), getMessageService(),
						getConvertorService());
			}
		};
	}

}

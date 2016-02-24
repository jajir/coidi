package com.coroptis.coidi.rp.iocsupport;

import com.coroptis.coidi.core.util.CoreBinding;
import com.coroptis.coidi.rp.services.AssociationFactory;
import com.coroptis.coidi.rp.services.AssociationHelper;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthRespSupport;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;
import com.coroptis.coidi.rp.services.CoidiRp;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoveryService;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.HttpTransportService;
import com.coroptis.coidi.rp.services.NonceStorage;
import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.coroptis.coidi.rp.services.RpService;
import com.coroptis.coidi.rp.services.XmlProcessing;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.services.YadisService;
import com.coroptis.coidi.rp.services.impl.AssociationFactoryImpl;
import com.coroptis.coidi.rp.services.impl.AssociationHelperImpl;
import com.coroptis.coidi.rp.services.impl.AuthReqPreconditions;
import com.coroptis.coidi.rp.services.impl.AuthReqTerminator;
import com.coroptis.coidi.rp.services.impl.AuthReqUiIcon;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderOpenId;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderTerminator;
import com.coroptis.coidi.rp.services.impl.AuthRespOpenId20Verify;
import com.coroptis.coidi.rp.services.impl.AuthRespSupportImpl;
import com.coroptis.coidi.rp.services.impl.AuthenticationVerificationServiceImpl;
import com.coroptis.coidi.rp.services.impl.CoidiRpImpl;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorYadis;
import com.coroptis.coidi.rp.services.impl.DiscoveryServiceImpl;
import com.coroptis.coidi.rp.services.impl.DiscoverySupportImpl;
import com.coroptis.coidi.rp.services.impl.HttpServiceImpl;
import com.coroptis.coidi.rp.services.impl.HttpTranportServiceImpl;
import com.coroptis.coidi.rp.services.impl.NonceStoreInMemory;
import com.coroptis.coidi.rp.services.impl.RpServiceImpl;
import com.coroptis.coidi.rp.services.impl.XmlProcessingImpl;
import com.coroptis.coidi.rp.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.services.impl.YadisServiceImpl;
import com.google.common.base.Preconditions;

public class RpBinding extends CoreBinding {

	private final RpConfigurationService conf;

	public RpBinding(final RpConfigurationService conf) {
		this.conf = Preconditions.checkNotNull(conf, "Required RpConfigurationService is null");
	}

	/**
	 * {@link NonceStorage}
	 */
	private Lazy<NonceStorage> nonceStorage = new Lazy<NonceStorage>(initNonceStorage());

	public final NonceStorage getNonceStorage() {
		return nonceStorage.get();
	}

	protected Init<NonceStorage> initNonceStorage() {
		return new Init<NonceStorage>() {
			@Override
			public NonceStorage create() {
				return new NonceStoreInMemory();
			}
		};
	}

	/**
	 * {@link HttpService}
	 */
	private Lazy<HttpService> httpService = new Lazy<HttpService>(initHttpService());

	public final HttpService getHttpService() {
		return httpService.get();
	}

	protected Init<HttpService> initHttpService() {
		return new Init<HttpService>() {
			@Override
			public HttpService create() {
				return new HttpServiceImpl(conf);
			}
		};
	}

	/**
	 * {@link YadisService}
	 */
	private Lazy<YadisService> yadisService = new Lazy<YadisService>(initYadisService());

	public final YadisService getYadisService() {
		return yadisService.get();
	}

	protected Init<YadisService> initYadisService() {
		return new Init<YadisService>() {
			@Override
			public YadisService create() {
				return new YadisServiceImpl(getHttpService());
			}
		};
	}

	/**
	 * {@link XrdsService}
	 */
	private Lazy<XrdsService> xrdsService = new Lazy<XrdsService>(initXrdsService());

	public final XrdsService getXrdsService() {
		return xrdsService.get();
	}

	protected Init<XrdsService> initXrdsService() {
		return new Init<XrdsService>() {
			@Override
			public XrdsService create() {
				return new XrdsServiceImpl(getConvertorService());
			}
		};
	}

	/**
	 * {@link HttpTransportService}
	 */
	private Lazy<HttpTransportService> httpTransportService = new Lazy<HttpTransportService>(
			initHttpTransportService());

	public final HttpTransportService getHttpTransportService() {
		return httpTransportService.get();
	}

	protected Init<HttpTransportService> initHttpTransportService() {
		return new Init<HttpTransportService>() {
			@Override
			public HttpTransportService create() {
				return new HttpTranportServiceImpl(getHttpService());
			}
		};
	}

	/**
	 * {@link AssociationHelper}
	 */
	private Lazy<AssociationHelper> associationHelper = new Lazy<AssociationHelper>(initAssociationHelper());

	public final AssociationHelper getAssociationHelper() {
		return associationHelper.get();
	}

	protected Init<AssociationHelper> initAssociationHelper() {
		return new Init<AssociationHelper>() {
			@Override
			public AssociationHelper create() {
				return new AssociationHelperImpl();
			}
		};
	}

	/**
	 * {@link AssociationFactory}
	 */
	private Lazy<AssociationFactory> associationFactory = new Lazy<AssociationFactory>(initAssociationFactory());

	public final AssociationFactory getAssociationFactory() {
		return associationFactory.get();
	}

	protected Init<AssociationFactory> initAssociationFactory() {
		return new Init<AssociationFactory>() {
			@Override
			public AssociationFactory create() {
				return new AssociationFactoryImpl(getCryptoSessionService(), getConvertorService(),
						getHttpTransportService(), getAssociationHelper());
			}
		};
	}

	/**
	 * {@link XmlProcessing}
	 */
	private Lazy<XmlProcessing> xmlProcessing = new Lazy<XmlProcessing>(initXmlProcessing());

	public final XmlProcessing getXmlProcessing() {
		return xmlProcessing.get();
	}

	protected Init<XmlProcessing> initXmlProcessing() {
		return new Init<XmlProcessing>() {
			@Override
			public XmlProcessing create() {
				return new XmlProcessingImpl();
			}
		};
	}

	/**
	 * {@link DiscoverySupport}
	 */
	private Lazy<DiscoverySupport> discoverySupport = new Lazy<DiscoverySupport>(initDiscoverySupport());

	public final DiscoverySupport getDiscoverySupport() {
		return discoverySupport.get();
	}

	protected Init<DiscoverySupport> initDiscoverySupport() {
		return new Init<DiscoverySupport>() {
			@Override
			public DiscoverySupport create() {
				return new DiscoverySupportImpl(getHttpService(), getXrdsService());
			}
		};
	}

	/**
	 * {@link AuthRespSupport}
	 */
	private Lazy<AuthRespSupport> authRespSupport = new Lazy<AuthRespSupport>(initAuthRespSupport());

	public final AuthRespSupport getAuthRespSupport() {
		return authRespSupport.get();
	}

	protected Init<AuthRespSupport> initAuthRespSupport() {
		return new Init<AuthRespSupport>() {
			@Override
			public AuthRespSupport create() {
				return new AuthRespSupportImpl();
			}
		};
	}

	/**
	 * {@link DiscoveryProcessor}
	 */
	private Lazy<DiscoveryProcessor> discoveryProcessor = new Lazy<DiscoveryProcessor>(initDiscoveryProcessor());

	public final DiscoveryProcessor getDiscoveryProcessor() {
		return discoveryProcessor.get();
	}

	protected Init<DiscoveryProcessor> initDiscoveryProcessor() {
		return new Init<DiscoveryProcessor>() {
			@Override
			public DiscoveryProcessor create() {
				return new DiscoveryProcessorYadis(getHttpService(), getXrdsService(), getXmlProcessing(),
						getDiscoverySupport());
			}
		};
	}

	/**
	 * {@link DiscoveryService}
	 */
	private Lazy<DiscoveryService> discoveryService = new Lazy<DiscoveryService>(initDiscoveryService());

	public final DiscoveryService getDiscoveryService() {
		return discoveryService.get();
	}

	protected Init<DiscoveryService> initDiscoveryService() {
		return new Init<DiscoveryService>() {
			@Override
			public DiscoveryService create() {
				return new DiscoveryServiceImpl(getDiscoveryProcessor(), getDiscoverySupport());
			}
		};
	}

	/**
	 * {@link AuthReqPreconditions}
	 */
	private Lazy<AuthReq> authReqPreconditions = new Lazy<AuthReq>(initAuthReqPreconditions());

	public final AuthReq getAuthReqPreconditions() {
		return authReqPreconditions.get();
	}

	protected Init<AuthReq> initAuthReqPreconditions() {
		return new Init<AuthReq>() {
			@Override
			public AuthReq create() {
				return new AuthReqPreconditions();
			}
		};
	}

	/**
	 * {@link AuthReqUiIcon}
	 */
	private Lazy<AuthReq> authReqUiIcon = new Lazy<AuthReq>(initAuthReqUiIcon());

	public final AuthReq getAuthReqUiIcon() {
		return authReqUiIcon.get();
	}

	protected Init<AuthReq> initAuthReqUiIcon() {
		return new Init<AuthReq>() {
			@Override
			public AuthReq create() {
				return new AuthReqUiIcon();
			}
		};
	}

	/**
	 * {@link AuthReqTerminator}
	 */
	private Lazy<AuthReq> authReqTerminator = new Lazy<AuthReq>(initAuthReqTerminator());

	public final AuthReq getAuthReqTerminator() {
		return authReqTerminator.get();
	}

	protected Init<AuthReq> initAuthReqTerminator() {
		return new Init<AuthReq>() {
			@Override
			public AuthReq create() {
				return new AuthReqTerminator();
			}
		};
	}

	/**
	 * {@link AuthProcSimpleRp}
	 */
	private Lazy<AuthReq> authProcSimpleRp = new Lazy<AuthReq>(initAuthProcSimpleRp());

	public final AuthReq getAuthProcSimpleRp() {
		return authProcSimpleRp.get();
	}

	protected Init<AuthReq> initAuthProcSimpleRp() {
		return new Init<AuthReq>() {
			@Override
			public AuthReq create() {
				return new AuthProcSimpleRp(getAuthReqPreconditions(), getAuthReqUiIcon(), getAuthReqTerminator());
			}
		};
	}

	/**
	 * {@link AuthRespDecoderOpenId}
	 */
	private Lazy<AuthRespDecoder> authRespDecoderOpenId = new Lazy<AuthRespDecoder>(initAuthRespDecoderOpenId());

	public final AuthRespDecoder getAuthRespDecoderOpenId() {
		return authRespDecoderOpenId.get();
	}

	protected Init<AuthRespDecoder> initAuthRespDecoderOpenId() {
		return new Init<AuthRespDecoder>() {
			@Override
			public AuthRespDecoder create() {
				return new AuthRespDecoderOpenId(getNonceService(), getSigningService(), getNonceStorage());
			}
		};
	}

	/**
	 * {@link AuthRespDecoderTerminator}
	 */
	private Lazy<AuthRespDecoder> authRespDecoderTerminator = new Lazy<AuthRespDecoder>(
			initAuthRespDecoderTerminator());

	public final AuthRespDecoder getAuthRespDecoderTerminator() {
		return authRespDecoderTerminator.get();
	}

	protected Init<AuthRespDecoder> initAuthRespDecoderTerminator() {
		return new Init<AuthRespDecoder>() {
			@Override
			public AuthRespDecoder create() {
				return new AuthRespDecoderTerminator();
			}
		};
	}

	/**
	 * {@link AuthRespOpenId20Verify}
	 */
	private Lazy<AuthRespDecoder> authRespOpenId20Verify = new Lazy<AuthRespDecoder>(initAuthRespOpenId20Verify());

	public final AuthRespDecoder getAuthRespOpenId20Verify() {
		return authRespOpenId20Verify.get();
	}

	protected Init<AuthRespDecoder> initAuthRespOpenId20Verify() {
		return new Init<AuthRespDecoder>() {
			@Override
			public AuthRespDecoder create() {
				return new AuthRespOpenId20Verify();
			}
		};
	}

	/**
	 * {@link SimpleAuthResponseDecoder}
	 */
	private Lazy<AuthRespDecoder> simpleAuthResponseDecoder = new Lazy<AuthRespDecoder>(
			initSimpleAuthResponseDecoder());

	public final AuthRespDecoder getSimpleAuthResponseDecoder() {
		return simpleAuthResponseDecoder.get();
	}

	protected Init<AuthRespDecoder> initSimpleAuthResponseDecoder() {
		return new Init<AuthRespDecoder>() {
			@Override
			public AuthRespDecoder create() {
				return new SimpleAuthResponseDecoder(getAuthRespOpenId20Verify(), getAuthRespDecoderOpenId(),
						getAuthRespDecoderTerminator());
			}
		};
	}

	/**
	 * {@link RpService}
	 */
	private Lazy<RpService> rpService = new Lazy<RpService>(initRpService());

	public final RpService getRpService() {
		return rpService.get();
	}

	protected Init<RpService> initRpService() {
		return new Init<RpService>() {
			@Override
			public RpService create() {
				return new RpServiceImpl(conf, getAuthProcSimpleRp());
			}
		};
	}

	/**
	 * {@link AuthenticationVerificationService}
	 */
	private Lazy<AuthenticationVerificationService> authenticationVerificationService = new Lazy<AuthenticationVerificationService>(
			initAuthenticationVerificationService());

	public final AuthenticationVerificationService getAuthenticationVerificationService() {
		return authenticationVerificationService.get();
	}

	protected Init<AuthenticationVerificationService> initAuthenticationVerificationService() {
		return new Init<AuthenticationVerificationService>() {
			@Override
			public AuthenticationVerificationService create() {
				return new AuthenticationVerificationServiceImpl(getSimpleAuthResponseDecoder());
			}
		};
	}

	/**
	 * {@link CoidiRp}
	 */
	private Lazy<CoidiRp> coidiRp = new Lazy<CoidiRp>(initCoidiRp());

	public final CoidiRp getCoidiRp() {
		return coidiRp.get();
	}

	protected Init<CoidiRp> initCoidiRp() {
		return new Init<CoidiRp>() {
			@Override
			public CoidiRp create() {
				return new CoidiRpImpl(getAssociationFactory(), getDiscoveryProcessor(),
						getAuthenticationVerificationService(), getMessageService(), getRpService());
			}
		};
	}

}

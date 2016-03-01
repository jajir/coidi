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
	public NonceStorage getNonceStorage() {
		NonceStorage out = get(NonceStorage.class);
		if (out == null) {
			out = new NonceStoreInMemory();
			put(NonceStorage.class, out);
		}
		return out;
	}

	/**
	 * {@link HttpService}
	 */
	public HttpService getHttpService() {
		HttpService out = get(HttpService.class);
		if (out == null) {
			out = new HttpServiceImpl(conf);
			put(HttpService.class, out);
		}
		return out;
	}

	/**
	 * {@link YadisService}
	 */
	public YadisService getYadisService() {
		YadisService out = get(YadisService.class);
		if (out == null) {
			out = new YadisServiceImpl(getHttpService());
			put(YadisService.class, out);
		}
		return out;
	}

	/**
	 * {@link XrdsService}
	 */
	public XrdsService getXrdsService() {
		XrdsService out = get(XrdsService.class);
		if (out == null) {
			out = new XrdsServiceImpl(getConvertorService());
			put(XrdsService.class, out);
		}
		return out;
	}

	/**
	 * {@link HttpTransportService}
	 */
	public HttpTransportService getHttpTransportService() {
		HttpTransportService out = get(HttpTransportService.class);
		if (out == null) {
			out = new HttpTranportServiceImpl(getHttpService());
			put(HttpTransportService.class, out);
		}
		return out;
	}

	/**
	 * {@link AssociationHelper}
	 */
	public AssociationHelper getAssociationHelper() {
		AssociationHelper out = get(AssociationHelper.class);
		if (out == null) {
			out = new AssociationHelperImpl();
			put(AssociationHelper.class, out);
		}
		return out;
	}

	/**
	 * {@link AssociationFactory}
	 */
	public AssociationFactory getAssociationFactory() {
		AssociationFactory out = get(AssociationFactory.class);
		if (out == null) {
			out = new AssociationFactoryImpl(getCryptoSessionService(), getConvertorService(),
					getHttpTransportService(), getAssociationHelper());
			put(AssociationFactory.class, out);
		}
		return out;
	}

	/**
	 * {@link XmlProcessing}
	 */
	public XmlProcessing getXmlProcessing() {
		XmlProcessing out = get(XmlProcessing.class);
		if (out == null) {
			out = new XmlProcessingImpl();
			put(XmlProcessing.class, out);
		}
		return out;
	}

	/**
	 * {@link DiscoverySupport}
	 */
	public DiscoverySupport getDiscoverySupport() {
		DiscoverySupport out = get(DiscoverySupport.class);
		if (out == null) {
			out = new DiscoverySupportImpl(getHttpService(), getXrdsService());
			put(DiscoverySupport.class, out);
		}
		return out;
	}

	/**
	 * {@link AuthRespSupport}
	 */
	public AuthRespSupport getAuthRespSupport() {
		AuthRespSupport out = get(AuthRespSupport.class);
		if (out == null) {
			out = new AuthRespSupportImpl();
			put(AuthRespSupport.class, out);
		}
		return out;
	}

	/**
	 * {@link DiscoveryProcessor}
	 */
	public DiscoveryProcessor getDiscoveryProcessor() {
		DiscoveryProcessor out = get(DiscoveryProcessor.class);
		if (out == null) {
			out = new DiscoveryProcessorYadis(getHttpService(), getXrdsService(), getXmlProcessing(),
					getDiscoverySupport());
			put(DiscoveryProcessor.class, out);
		}
		return out;
	}

	/**
	 * {@link DiscoveryService}
	 */
	public DiscoveryService getDiscoveryService() {
		DiscoveryService out = get(DiscoveryService.class);
		if (out == null) {
			out = new DiscoveryServiceImpl(getDiscoveryProcessor(), getDiscoverySupport());
			put(DiscoveryService.class, out);
		}
		return out;
	}

	/**
	 * {@link AuthReqPreconditions}
	 */
	public AuthReq getAuthReqPreconditions() {
		AuthReq out = get("AuthReqPreconditions");
		if (out == null) {
			out = new AuthReqPreconditions();
			put("AuthReqPreconditions", out);
		}
		return out;
	}

	/**
	 * {@link AuthReqUiIcon}
	 */
	public AuthReq getAuthReqUiIcon() {
		AuthReq out = get("AuthReqUiIcon");
		if (out == null) {
			out = new AuthReqUiIcon();
			put("AuthReqUiIcon", out);
		}
		return out;
	}

	/**
	 * {@link AuthReqTerminator}
	 */
	public AuthReq getAuthReqTerminator() {
		AuthReq out = get("AuthReqTerminator");
		if (out == null) {
			out = new AuthReqTerminator();
			put("AuthReqTerminator", out);
		}
		return out;
	}

	/**
	 * {@link AuthProcSimpleRp}
	 */
	public AuthReq getAuthProcSimpleRp() {
		AuthReq out = get("AuthProcSimpleRp");
		if (out == null) {
			AuthReqChain chain = new AuthReqChain();
			chain.add(getAuthReqPreconditions());
			chain.add(getAuthReqUiIcon());
			chain.add(getAuthReqTerminator());
			put("AuthProcSimpleRp", chain);
			out = chain;
		}
		return out;
	}

	/**
	 * {@link AuthRespDecoderOpenId}
	 */
	public AuthRespDecoder getAuthRespDecoderOpenId() {
		AuthRespDecoder out = get("AuthRespDecoderOpenId");
		if (out == null) {
			out = new AuthRespDecoderOpenId(getNonceService(), getSigningService(), getNonceStorage());
			put("AuthRespDecoderOpenId", out);
		}
		return out;
	}

	/**
	 * {@link AuthRespDecoderTerminator}
	 */
	public AuthRespDecoder getAuthRespDecoderTerminator() {
		AuthRespDecoder out = get("AuthRespDecoderTerminator");
		if (out == null) {
			out = new AuthRespDecoderTerminator();
			put("AuthRespDecoderTerminator", out);
		}
		return out;
	}

	/**
	 * {@link AuthRespOpenId20Verify}
	 */
	public AuthRespDecoder getAuthRespOpenId20Verify() {
		AuthRespDecoder out = get("AuthRespOpenId20Verify");
		if (out == null) {
			out = new AuthRespOpenId20Verify();
			put("AuthRespOpenId20Verify", out);
		}
		return out;
	}

	/**
	 * {@link AuthResponseDecoderChain}
	 */
	public AuthRespDecoder getSimpleAuthResponseDecoder() {
		AuthRespDecoder out = get("simpleAuthResponseDecoder");
		if (out == null) {
			AuthResponseDecoderChain chain = new AuthResponseDecoderChain();
			chain.add(getAuthRespOpenId20Verify());
			chain.add(getAuthRespDecoderOpenId());
			chain.add(getAuthRespDecoderTerminator());
			put("simpleAuthResponseDecoder", chain);
			out = chain;
		}
		return out;
	}

	/**
	 * {@link RpService}
	 */
	public RpService getRpService() {
		RpService out = get(RpService.class);
		if (out == null) {
			out = new RpServiceImpl(conf, getAuthProcSimpleRp());
			put(RpService.class, out);
		}
		return out;
	}

	/**
	 * {@link AuthenticationVerificationService}
	 */
	public AuthenticationVerificationService getAuthenticationVerificationService() {
		AuthenticationVerificationService out = get(AuthenticationVerificationService.class);
		if (out == null) {
			out = new AuthenticationVerificationServiceImpl(getSimpleAuthResponseDecoder());
			put(AuthenticationVerificationService.class, out);
		}
		return out;
	}

	/**
	 * {@link CoidiRp}
	 */
	public CoidiRp getCoidiRp() {
		CoidiRp out = get(CoidiRp.class);
		if (out == null) {
			out = new CoidiRpImpl(getAssociationFactory(), getDiscoveryProcessor(),
					getAuthenticationVerificationService(), getMessageService(), getRpService());
			put(CoidiRp.class, out);
		}
		return out;
	}

}

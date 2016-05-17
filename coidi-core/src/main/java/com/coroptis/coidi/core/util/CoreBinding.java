package com.coroptis.coidi.core.util;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.core.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptoSessionServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptographyServiceImpl;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;
import com.coroptis.coidi.core.services.impl.NonceToolImpl;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;

/**
 * Initialize coidi core classes.
 * 
 * @author jiroutj
 *
 */
public class CoreBinding extends AbstractBinding {

    /**
     * @return {@link ConvertorService}
     */
    public final ConvertorService getConvertorService() {
	ConvertorService out = get(ConvertorService.class);
	if (out == null) {
	    out = new ConvertorServiceImpl();
	    put(ConvertorService.class, out);
	}
	return out;
    }

    /**
     * @return {@link NonceTool}
     */
    public NonceTool getNonceTool() {
    	NonceTool out = get(NonceTool.class);
	if (out == null) {
	    out = new NonceToolImpl(getConvertorService());
	    put(NonceTool.class, out);
	}
	return out;
    }

    /**
     * @return {@link MessageService}
     */
    public MessageService getMessageService() {
	MessageService out = get(MessageService.class);
	if (out == null) {
	    out = new MessageServiceImpl();
	    put(MessageService.class, out);
	}
	return out;
    }

    /**
     * @return {@link CryptographyService}
     */
    public CryptographyService getCryptographyService() {
	CryptographyService out = get(CryptographyService.class);
	if (out == null) {
	    out = new CryptographyServiceImpl();
	    put(CryptographyService.class, out);
	}
	return out;
    }

    /**
     * @return {@link CryptoSessionService}
     */
    public CryptoSessionService getCryptoSessionService() {
	CryptoSessionService out = get(CryptoSessionService.class);
	if (out == null) {
	    out = new CryptoSessionServiceImpl(getCryptographyService());
	    put(CryptoSessionService.class, out);
	}
	return out;
    }

    /**
     * @return {@link SigningService}
     */
    public SigningService getSigningService() {
	SigningService service = get(SigningService.class);
	if (service == null) {
	    service = new SigningServiceImpl(getCryptographyService(), getMessageService(),
		    getConvertorService());
	    put(SigningService.class, service);
	}
	return service;
    }

}

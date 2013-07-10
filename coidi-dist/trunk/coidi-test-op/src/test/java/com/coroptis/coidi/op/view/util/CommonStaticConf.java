package com.coroptis.coidi.op.view.util;

import com.coroptis.coidi.op.services.AssociationTool;

/**
 * This allows to extract static T5 configuration into separate method that
 * could be called from all test that initialized T5.
 * 
 * @author jirout
 * 
 */
public class CommonStaticConf {

    /**
     * Configure coidi through system properties.
     */
    public static final void conf() {
	System.setProperty("server.role", "junit");
	System.setProperty("system.property.configuration.directory", "non-existing");
	System.setProperty("op.err.contact", "john@yahoo.com");
	System.setProperty("op.identity.pattern", "http://www.myid.com/%identity%/");
	System.setProperty("op.openid.version11.enabled", "true");
	System.setProperty(AssociationTool.DEFAULT_ASSOCITION_TYPE, "HMAC-SHA1");
	System.setProperty("op.server", "http://localhost:8080/");
	System.setProperty(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS, "1800");
    }

}

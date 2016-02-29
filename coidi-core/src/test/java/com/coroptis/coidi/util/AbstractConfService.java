package com.coroptis.coidi.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.iocsupport.OpConfigurationServiceImpl;
import com.google.common.base.Preconditions;

public class AbstractConfService {

    private final Properties prop;

    public AbstractConfService(final String propertyFileName) {
	Preconditions.checkNotNull(propertyFileName);
	prop = new Properties();
	try {
	    prop.load(new BufferedInputStream(OpConfigurationServiceImpl.class.getClassLoader()
		    .getResourceAsStream(propertyFileName)));
	} catch (IOException e) {
	    throw new CoidiException(e.getMessage(), e);
	}
    }

    /**
     * @return the prop
     */
    protected Properties getProp() {
	return prop;
    }

}

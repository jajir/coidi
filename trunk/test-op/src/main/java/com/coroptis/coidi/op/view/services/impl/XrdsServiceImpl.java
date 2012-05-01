package com.coroptis.coidi.op.view.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.conf.services.ConfigurationService;
import com.coroptis.coidi.op.view.services.XrdsService;
import com.google.common.base.Preconditions;

public class XrdsServiceImpl implements XrdsService {

	@Inject
	private ConfigurationService configurationService;

	@Override
	public String getDocument(final String user) throws CoidiException {
		Preconditions.checkNotNull(user, "user name");
		Map<String, String> conf = configurationService
				.loadDefaultConfiguration("op");
		StringBuilder buff = new StringBuilder(1000);
		buff.append("<Service xmlns=\"xri://$xrd*($v*2.0)\">\n");
		buff.append("  <Type>http://specs.openid.net/auth/2.0/signon</Type>\n");
		buff.append("  <URI>");
		buff.append(conf.get("server"));
		buff.append("endpoint</URI>\n");
		buff.append("  <LocalID>");
		buff.append(conf.get("server"));
		buff.append("user/");
		buff.append(user);
		buff.append("</LocalID>\n");
		buff.append("</Service>");
		return buff.toString();
	}

}

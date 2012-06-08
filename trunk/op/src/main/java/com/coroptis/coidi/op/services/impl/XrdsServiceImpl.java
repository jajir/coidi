package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.op.services.XrdsService;
import com.google.common.base.Preconditions;

public class XrdsServiceImpl implements XrdsService {

	@Inject
	private ConfigurationService configurationService;
	
	@Inject
	@Symbol("op.server")
	private String server;

	@Override
	public String getDocument(final String user) throws CoidiException {
		Preconditions.checkNotNull(user, "user name");
		Map<String, String> conf = configurationService
				.loadDefaultConfiguration("op");
		StringBuilder buff = new StringBuilder(1000);
		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buff.append("<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\" xmlns:openid=\"http://openid.net/xmlns/1.0\">\n");
		buff.append("  <XRD>\n");
		buff.append("    <Service xmlns=\"xri://$xrd*($v*2.0)\">\n");
		buff.append("      <Type>http://specs.openid.net/auth/2.0/signon</Type>\n");
		buff.append("      <URI>");
		buff.append(conf.get("server"));
		buff.append("openid</URI>\n");
		buff.append("      <LocalID>");
		buff.append(conf.get("server"));
		buff.append("user/");
		buff.append(user);
		buff.append("</LocalID>\n");
		buff.append("    </Service>\n");
		buff.append("  </XRD>\n");
		buff.append("</xrds:XRDS>");
		return buff.toString();
	}

	@Override
	public String getXrdsLocation(final String userName) {
		Preconditions.checkNotNull(userName, "user name");
		StringBuilder buff = new StringBuilder(100);
		buff.append(server);
		buff.append("userxrds/");
		buff.append(userName);
		return buff.toString();
	}

}

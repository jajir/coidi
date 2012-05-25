package com.coroptis.coidi.core.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AbstractOpenIdRequest extends AbstractMessage {

	public AbstractOpenIdRequest() {
		super();
	}

	public AbstractOpenIdRequest(final Map<String, String> map) {
		super(map, OPENID);
	}

	public String getMessage() {
		return getPrefixedMessage(OPENID);
	}
	
	@Override
	protected Map<String, String> getMap() {
		Map<String, String> out = new HashMap<String, String>();
		for (Entry<String, String> entry : super.getMap().entrySet()) {
			out.put(OPENID + entry.getKey(), entry.getValue());
		}
		return out;
	}

}

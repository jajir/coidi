package com.coroptis.coidi.core.message;

import java.util.Map;

public class AbstractOpenIdRequest extends AbstractMessage {

	public AbstractOpenIdRequest(){
		super();
	}
	
	public AbstractOpenIdRequest(final Map<String, String> map){
		super(map);
	}

	public String getMessage() {
		return getPrefixedMessage(OPENID);
	}

}

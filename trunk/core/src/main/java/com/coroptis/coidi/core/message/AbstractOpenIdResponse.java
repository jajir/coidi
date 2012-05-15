package com.coroptis.coidi.core.message;

import java.util.Map;

public class AbstractOpenIdResponse extends AbstractMessage {

	public AbstractOpenIdResponse(){
		super();
	}
	
	public AbstractOpenIdResponse(final Map<String, String> map){
		super(map);
	}

	public String getMessage(){
		return getPrefixedMessage(null);
	}

}

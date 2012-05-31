package com.coroptis.coidi.rp.services;

import java.util.Map;

public interface HttpTransportService {

	Map<String, String> doPost(String url, Map<String, String> map);
	
}

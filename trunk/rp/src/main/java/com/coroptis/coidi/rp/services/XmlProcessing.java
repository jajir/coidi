package com.coroptis.coidi.rp.services;

import org.w3c.dom.Node;

public interface XmlProcessing {
	
	boolean isThereTag(Node node, String tagName, String value);
	
	String getTagContent(Node node, String tagName);
	
}

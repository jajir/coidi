package com.coroptis.coidi.rp.services.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.coroptis.coidi.rp.services.XmlProcessing;

public class XmlProcessingImpl implements XmlProcessing {
	
	@Override
	public boolean isThereTag(Node node, String tagName, String value) {
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (value.equals(n.getTextContent())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getTagContent(Node node, String tagName) {
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (tagName.equals(n.getNodeName())) {
				return n.getTextContent();
			}
		}
		return null;
	}

}

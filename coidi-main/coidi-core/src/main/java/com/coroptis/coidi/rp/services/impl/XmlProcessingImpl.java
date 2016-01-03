/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.rp.services.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.coroptis.coidi.rp.services.XmlProcessing;
import com.google.common.base.Preconditions;

public class XmlProcessingImpl implements XmlProcessing {

    @Inject
    private Logger logger;

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

    @Override
    public String getMetaContent(final String htmlDocument, final String name) {
	logger.debug("reading META HTML tag '" + name + "' from document: " + htmlDocument);
	final Pattern pattern = Pattern.compile("<meta\\s*http-equiv=\"" + name + "\".*.>");
	final Matcher matcher = pattern.matcher(htmlDocument);
	while (matcher.find()) {
	    logger.debug("found: " + matcher.group() + ", " + matcher.start() + ", "
		    + matcher.end());
	    return extractAttributeFromTag(matcher.group(), "content");
	}
	return null;
    }

    private String extractAttributeFromTag(final String tag, final String attribute) {
	Pattern pattern = Pattern.compile(attribute + "\\s*=\\s*\".*\"");
	Matcher matcher = pattern.matcher(tag);
	while (matcher.find()) {
	    final String attributePart = matcher.group();
	    final int firstQuotas = attributePart.indexOf('"');
	    final int lastQuotas = attributePart.lastIndexOf('"');
	    return attributePart.substring(firstQuotas + 1, lastQuotas);
	}
	return null;
    }

    @Override
    public Boolean isXrdsDocument(final String document) {
	Preconditions.checkNotNull(document, "document");
	if (document.startsWith("<?xml version=\"1.0\" ")) {
	    if (document.indexOf("xrds:XRDS") > 0) {
		return true;
	    }
	}
	return false;
    }
}

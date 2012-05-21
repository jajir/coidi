package com.coroptis.coidi.rp.view.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.view.services.XrdsService;
import com.coroptis.coidi.rp.view.util.XrdsServiceProvider;

public class XrdsServiceImpl implements XrdsService {

	Logger logger = Logger.getLogger(XrdsServiceImpl.class);

	@Override
	public String getEndpoint(String xrdsDocument) {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();

			builderFactory
					.setFeature(
							"http://apache.org/xml/features/nonvalidating/load-external-dtd",
							false);
			builderFactory.setFeature("http://xml.org/sax/features/validation",
					false);

			DocumentBuilder builder;
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new ByteArrayInputStream(
					xrdsDocument.getBytes()));

			Element element = document.getDocumentElement();
			element.normalize();

			System.out.println(element.getNodeName());
			NodeList nl = element.getElementsByTagName("Service");
			SortedSet<XrdsServiceProvider> services = new TreeSet<XrdsServiceProvider>();
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				XrdsServiceProvider provider = convert(node);
				if (provider != null) {
					services.add(provider);
				}
			}
			if (services.size() == 0) {
				return null;
			} else {
				return services.last().getUri();
			}
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

	private XrdsServiceProvider convert(Node node) {
		System.out.println(node.getNamespaceURI() + " ,  '"
				+ node.getNodeName() + "' size: "
				+ node.getChildNodes().getLength());
		if (isThereTag(node, "Type", "http://specs.openid.net/auth/2.0/signon")) {
			XrdsServiceProvider out = new XrdsServiceProvider();
			out.setDelegate(getTagValue(node, "openid:Delegate"));
			out.setUri(getTagValue(node, "URI"));
			if (node.getAttributes().getNamedItem("priority") != null) {
				out.setPriority(Integer.parseInt(node.getAttributes()
						.getNamedItem("priority").getTextContent()));
			}
			logger.debug(out);
			return out;
		}
		return null;
	}

	private boolean isThereTag(Node node, String tagName, String value) {
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (value.equals(n.getTextContent())) {
				return true;
			}
		}
		return false;
	}

	private String getTagValue(Node node, String tagName) {
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

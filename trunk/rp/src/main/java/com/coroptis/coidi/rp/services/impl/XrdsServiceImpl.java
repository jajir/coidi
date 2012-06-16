package com.coroptis.coidi.rp.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.XrdsService;

public class XrdsServiceImpl implements XrdsService {

	@Inject
	private Logger logger;

	@Inject
	private ConvertorService convertorService;

	@Override
	public DiscoveryResult extractDiscoveryResult(String xrdsDocument) {
		logger.debug("Starting processing XRDS document: " + xrdsDocument);
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
			DiscoveryResult result = new DiscoveryResult();
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				result.getServices().add(processServiceNode(node));
			}
			return result;
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

	private XrdService processServiceNode(Node serviceNode) {
		XrdService out = new XrdService();
		NodeList nl = serviceNode.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node.getNodeName().equals("Type")) {
				out.getTypes().add(node.getTextContent());
			}
			if (node.getNodeName().equals("URI")) {
				out.setUrl(node.getTextContent());
			}
		}
		if (serviceNode.getAttributes().getNamedItem("priority") != null) {
			out.setPriority(convertorService.getInt(serviceNode.getAttributes()
					.getNamedItem("priority").getTextContent()));
		}
		return out;
	}

}

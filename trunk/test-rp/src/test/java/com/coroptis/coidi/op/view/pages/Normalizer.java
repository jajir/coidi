package com.coroptis.coidi.op.view.pages;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;

public class Normalizer {

	private final static Logger logger = Logger.getLogger(Normalizer.class);

	private static final Pattern URL_PATTERN = Pattern.compile("^https?://",
			Pattern.CASE_INSENSITIVE);

	public String normalize(String identifier, boolean removeFragment)
			throws CoidiException {
		if (identifier.toLowerCase().startsWith("xri://")) {
			logger.debug("Dropping xri:// prefix from identifier: "
					+ identifier);
			identifier = identifier.substring(6);
			// it's XRI no more normalization
			return identifier;
		}

		if (!URL_PATTERN.matcher(identifier).find()) {
			identifier = "http://" + identifier;
		}
		return normalize2(identifier, removeFragment).toString();
	}

	public static URL normalize2(String text, boolean removeFragment)
			throws CoidiException {
		try {
			URI uri = new URI(text);
			URL url = uri.normalize().toURL();

			String protocol = url.getProtocol().toLowerCase();
			String host = url.getHost().toLowerCase();
			int port = url.getPort();
			String path = url.getPath();
			String query = url.getQuery();
			String fragment = url.getRef();

			if (port == url.getDefaultPort())
				port = -1;

			// start building the 'file' part for the URL constructor...
			String file = path;

			if ("".equals(file))
				file = "/";

			if (query != null)
				file = file + "?" + query;

			if (fragment != null && !removeFragment)
				file = file + "#" + fragment;

			URL normalized = new URL(protocol, host, port, file);

			logger.debug("Normalized: " + text + " to: " + normalized);

			return normalized;
		} catch (MalformedURLException e) {
			throw new CoidiException("Invalid URL identifier", e);
		} catch (URISyntaxException e) {
			throw new CoidiException("Invalid URL identifier", e);
		}
	}

}

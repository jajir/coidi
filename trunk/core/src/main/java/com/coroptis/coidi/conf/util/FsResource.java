package com.coroptis.coidi.conf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.ioc.Resource;

/**
 * Allows read file
 * 
 * @author jan
 * 
 */
public class FsResource implements Resource {

	private final File file;

	public FsResource(final String path) {
		this.file = new File(path);
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public Resource forFile(String relativePath) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Resource forLocale(Locale locale) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String getFile() {
		return file.getAbsolutePath();
	}

	@Override
	public String getFolder() {
		return file.getParent();
	}

	@Override
	public String getPath() {
		return file.getAbsolutePath();
	}

	@Override
	public InputStream openStream() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public URL toURL() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Resource withExtension(String extension) {
		throw new UnsupportedOperationException("not implemented");
	}

}

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
package com.coroptis.coidi.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.ioc.Resource;

import com.coroptis.coidi.CoidiException;

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
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new CoidiException(e.getMessage(), e);
		}
	}

	@Override
	public Resource withExtension(String extension) {
		throw new UnsupportedOperationException("not implemented");
	}

}

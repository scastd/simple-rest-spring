package com.example.restspringtemplate.net.http;

import com.google.gson.JsonObject;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.example.restspringtemplate.utils.Constants.GSON;

/**
 * Class that wraps an {@link HttpServletRequest} and provides methods to
 * read multiple times the body of the request as a {@link JsonObject}.
 * Also, it provides a way to store data in the request, which can be retrieved
 * later.
 */
public class HttpRequest extends HttpServletRequestWrapper {
	private final byte[] cachedBody;
	private final Map<String, Object> data = new HashMap<>();
	private final JsonObject parsedBody;

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request the request to wrap.
	 *
	 * @throws IllegalArgumentException if the request is null.
	 */
	public HttpRequest(HttpServletRequest request) throws IOException {
		super(request);
		this.cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
		this.parsedBody = GSON.fromJson(IOUtils.toString(this.getReader()), JsonObject.class);
	}

	/**
	 * @return the cached body as a {@link CachedBodyServletInputStream}.
	 */
	@Override
	public ServletInputStream getInputStream() {
		return new CachedBodyServletInputStream(this.cachedBody);
	}

	/**
	 * @return a new {@link BufferedReader} for the cached body byte array.
	 */
	@Override
	public BufferedReader getReader() {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
		return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

	/**
	 * @return the body of the request as a {@link JsonObject}.
	 */
	public JsonObject body() {
		return this.parsedBody;
	}

	/**
	 * Returns the value of the given key in the data attribute of the request and
	 * removes it from the map.
	 *
	 * @param key the key to get the value of.
	 *
	 * @return the value of the given key in the data of the request.
	 *
	 * @implNote This method uses the custom "data" attribute that is present in this
	 * class, but it is not an HTTP standard.
	 */
	public Object get(String key) {
		return this.data.remove(key);
	}

	/**
	 * Adds (or changes) the value of the given key in the data attribute
	 * of the request.
	 *
	 * @param key   the key to set the value of.
	 * @param value the value to set.
	 *
	 * @implNote This method uses the custom "data" attribute that is present in this
	 * class, but it is not an HTTP standard.
	 */
	public void set(String key, Object value) {
		this.data.put(key, value);
	}

	/**
	 * Wrapper class for the cached body. This class is used to provide a way to
	 * read the body multiple times.
	 */
	static class CachedBodyServletInputStream extends ServletInputStream {
		private final InputStream cachedBodyInputStream;

		/**
		 * Constructs a new {@link CachedBodyServletInputStream} for the given
		 * byte array body.
		 *
		 * @param cachedBody the byte array body to wrap.
		 */
		public CachedBodyServletInputStream(byte[] cachedBody) {
			this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isFinished() {
			try {
				return this.cachedBodyInputStream.available() == 0;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isReady() {
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setReadListener(ReadListener listener) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read() throws IOException {
			return this.cachedBodyInputStream.read();
		}
	}
}

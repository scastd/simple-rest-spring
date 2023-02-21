package com.example.restspringtemplate.net.http;

import com.example.restspringtemplate.exceptions.InternalServerException;
import com.example.restspringtemplate.utils.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static com.example.restspringtemplate.utils.Constants.DATA_JSON_KEY;
import static com.example.restspringtemplate.utils.Constants.ERROR_JSON_KEY;
import static com.example.restspringtemplate.utils.Constants.GSON;
import static com.example.restspringtemplate.utils.Constants.OBJECT_MAPPER;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class that simplifies the management of the response to the client.
 * Wraps the {@link HttpServletResponse} class, to add more functionality, such as
 * setting the status of the response, and sending the response to the client in an easier way.
 */
@Slf4j
public class HttpResponse extends HttpServletResponseWrapper {
	private HttpStatus status = null;

	/**
	 * Constructs a response adaptor wrapping the given response.
	 *
	 * @param response the response to be wrapped.
	 *
	 * @throws IllegalArgumentException if the response is null.
	 */
	public HttpResponse(HttpServletResponse response) {
		super(response);
	}

	/**
	 * Sets the status of the response.
	 *
	 * @param status the status to be set.
	 *
	 * @return this response with the status set.
	 */
	public HttpResponse status(HttpStatus status) {
		this.status = status;
		this.setStatus(status.value());
		return this;
	}

	/**
	 * Sets the status of the response to {@link HttpStatus#OK}.
	 *
	 * @return this response with the status set.
	 */
	public HttpResponse ok() {
		return this.status(HttpStatus.OK);
	}

	/**
	 * Sets the status of the response to {@link HttpStatus#CREATED}.
	 * This method also sets the Location header to the location of the created resource.
	 *
	 * @param requestURL the URL of the request to set the Location header path.
	 *
	 * @return this response with the status set.
	 */
	public HttpResponse created(String requestURL) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
		                                                .path(requestURL)
		                                                .toUriString());
		this.setHeader(LOCATION, uri.toString());
		return this.status(HttpStatus.CREATED);
	}

	/**
	 * Sets the status of the response to {@link HttpStatus#BAD_REQUEST}.
	 *
	 * @return this response with the status set.
	 */
	public HttpResponse badRequest() {
		return this.status(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Sets the status of the response to {@link HttpStatus#NOT_FOUND}.
	 *
	 * @return this response with the status set.
	 */
	public HttpResponse notFound() {
		return this.status(HttpStatus.NOT_FOUND);
	}

	/**
	 * Immediately sends a response with the given status and empty body.
	 *
	 * @param status the status to be set.
	 *
	 * @throws IOException if an error occurs while sending the response.
	 */
	public void sendStatus(HttpStatus status) throws IOException {
		this.status(status).send(HttpResponseBody.EMPTY);
	}

	/**
	 * Immediately sends a response with the current status and empty body.
	 *
	 * @throws IOException if an error occurs while sending the response.
	 */
	public void send() throws IOException {
		this.send(HttpResponseBody.EMPTY);
	}

	/**
	 * Sends the given body to the client.
	 * <p>
	 * If it is an error, the key is set to {@link Constants#ERROR_JSON_KEY}.
	 * If not, the key is set to {@link Constants#DATA_JSON_KEY}.
	 *
	 * @param content the content to be sent.
	 *
	 * @throws IOException if an error occurs while sending the response.
	 */
	public void send(Object content) throws IOException {
		HttpResponseBody body;

		if (this.status.isError()) {
			body = new HttpResponseBody(ERROR_JSON_KEY, content);
		} else {
			body = new HttpResponseBody(DATA_JSON_KEY, content);
		}

		this.send(body);
	}

	/**
	 * Sends a response with the given body.
	 *
	 * @param body the body of the response to be sent.
	 *
	 * @throws IOException             if an error occurs while sending the response.
	 * @throws InternalServerException if the status of the response is not set.
	 */
	public void send(HttpResponseBody body) throws IOException {
		if (this.status == null) {
			log.error("Http response status must not be null");
			throw new InternalServerException("Please try again later.");
		}

		this.setContentType(APPLICATION_JSON_VALUE);

		if (body == HttpResponseBody.EMPTY) {
			// This is done like this to send a completely empty response body.
			this.getWriter().print(body.value()); // Empty string
			return;
		}

		JsonElement responseBody = body.data;

		// If the response body contains only one element, send it directly (without the key)
		// to simplify the json sent to the client.
		if (body.data.size() == 1) {
			responseBody = body.value();
		}

		// Serialize the response body to json and send it to the client.
		OBJECT_MAPPER.writeValue(this.getWriter(), responseBody);
	}

	/**
	 * Class that represents the body of a response.
	 * Contains a {@link JsonObject} of elements to be sent in the response.
	 */
	public static class HttpResponseBody {
		/**
		 * An empty response body.
		 */
		public static final HttpResponseBody EMPTY = new HttpResponseBody(DATA_JSON_KEY, "");
		private final JsonObject data = new JsonObject();

		/**
		 * Constructs a response body with the given key and value.
		 *
		 * @param key   the key of the element to be sent.
		 * @param value the value of the element to be sent.
		 */
		public HttpResponseBody(String key, Object value) {
			this.add(key, value);
		}

		/**
		 * Adds an element to the body.
		 *
		 * @param key   the key of the element to be added.
		 * @param value the value of the element to be added.
		 *
		 * @return this response body with the element added.
		 */
		public HttpResponseBody add(String key, Object value) {
			this.data.add(key, GSON.toJsonTree(value));
			return this;
		}

		/**
		 * Used when the response body contains only one element.
		 *
		 * @return the value of the first element in the body.
		 */
		public JsonElement value() {
			return this.data.asMap().values().iterator().next();
		}
	}
}

package com.example.restspringtemplate.config.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Class that configures all the routes that the application can handle.
 */
public class RouterSecurityConfig {
	private final HttpSecurity http;
	private final String[] authorizedRoles;

	/**
	 * Constructor that initializes the class with the http security configuration.
	 *
	 * @param http            the http security configuration.
	 * @param authorizedRoles the authorized roles for the routes.
	 */
	public RouterSecurityConfig(HttpSecurity http, String[] authorizedRoles) {
		this.http = http;
		this.authorizedRoles = authorizedRoles;
	}

	/**
	 * Method that configures the routes that can be accessed by the user.
	 *
	 * @param method the method used to access the routes.
	 * @param routes all the routes that can be accessed by the user.
	 *
	 * @return this router security config.
	 *
	 * @throws Exception if an error occurs.
	 */
	public RouterSecurityConfig registerRoutes(HttpMethod method, String... routes) throws Exception {
		this.http.authorizeHttpRequests()
		         .requestMatchers(method, routes)
		         .hasAnyAuthority(this.authorizedRoles);

		return this;
	}
}

package com.example.restspringtemplate.config.security.filter;

import com.example.restspringtemplate.config.security.JWTService;
import com.example.restspringtemplate.net.http.HttpResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.example.restspringtemplate.router.Routes.TEST_URL;
import static com.example.restspringtemplate.utils.Constants.JWT_TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Filter that checks if the user is authorized to access the resource.
 */
@Slf4j
@WebFilter(filterName = "AuthorizationFilter")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
	private final JWTService jwtService;

	/**
	 * Checks if the user is authorized to access the resource, sets the authentication
	 * and passes the request to the next filter.
	 *
	 * @param request  HTTP request.
	 * @param response HTTP response.
	 * @param chain    the filter chain.
	 *
	 * @throws IOException      if an I/O error occurs.
	 * @throws ServletException if a servlet error occurs.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain chain) throws ServletException, IOException {
		if (this.isUnknownPath(request.getServletPath())) {
			new HttpResponse(response).sendStatus(NOT_FOUND);
			return;
		}

		if (this.isExcludedPath(request.getServletPath())) {
			chain.doFilter(request, response);
			return;
		}

		String token = request.getHeader(AUTHORIZATION);

		if (token == null || !token.startsWith(JWT_TOKEN_PREFIX)) {
			// All routes need the JWT token except for the routes excluded above.
			log.error("Authorization header is missing or invalid.");
			throw new ServletException("Missing or invalid Authorization header.");
		}

		try {
			String username = this.jwtService.getUsername(token);
			String role = this.jwtService.getClaim(token, claims -> claims.get("role", String.class));

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(
					username, null, Collections.singleton(authority)
				);

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			new HttpResponse(response).status(FORBIDDEN)
			                          .send(e.getMessage());
		}
	}

	/**
	 * Checks if the path is excluded from the authorization filter.
	 *
	 * @param path the path to check.
	 *
	 * @return {@code true} if the path is excluded, {@code false} otherwise.
	 */
	private boolean isExcludedPath(String path) {
		return path.equals(TEST_URL);
	}

	/**
	 * Checks if the path is unknown.
	 *
	 * @param path the path to check.
	 *
	 * @return {@code true} if the path is unknown, {@code false} otherwise.
	 */
	private boolean isUnknownPath(String path) {
		return path.equals("/favicon.ico");
	}
}

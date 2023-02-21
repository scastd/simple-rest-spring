package com.example.restspringtemplate.config.security.filter;

import com.example.restspringtemplate.config.security.JWTService;
import com.example.restspringtemplate.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.example.restspringtemplate.router.Routes.PostRoute.LOGIN_URL;

@Configuration
@RequiredArgsConstructor
public class FilterBeans {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JWTService jwtService;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userService);
		provider.setPasswordEncoder(this.passwordEncoder);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(this.authenticationProvider()));
	}

	@Bean
	public AuthenticationFilter authenticationFilter() {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(
			this.authenticationManager(),
			this.jwtService
		);

		authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
		return authenticationFilter;
	}

	@Bean
	public AuthorizationFilter authorizationFilter() {
		return new AuthorizationFilter(this.jwtService);
	}
}

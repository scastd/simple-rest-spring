package com.example.restspringtemplate.config.security;

import com.example.restspringtemplate.router.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.example.restspringtemplate.utils.Constants.ACCEPTED_ORIGINS;
import static com.example.restspringtemplate.utils.Constants.STRING_ARRAY;

/**
 * Class that configures the CORS for the application.
 */
@Configuration
public class CorsConfig {
	/**
	 * Method that configures the CORS for the application.
	 *
	 * @return {@link WebMvcConfigurer} with the CORS configuration.
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(Routes.ALL_ROUTES)
				        .allowedMethods(CorsConfiguration.ALL)
				        .allowedOrigins(ACCEPTED_ORIGINS.toArray(STRING_ARRAY));
			}
		};
	}
}

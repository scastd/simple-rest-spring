package com.example.restspringtemplate.utils;

import com.example.restspringtemplate.json.modules.GsonModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

/**
 * Constants used throughout the application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
	public static final List<String> ACCEPTED_ORIGINS = List.of(
		// Local development
		"http://localhost:3000"
		// Production deployments
	);

	public static final Gson GSON = new GsonBuilder().create();
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
		.setSerializationInclusion(JsonInclude.Include.NON_NULL) // Don't serialize null values
		.registerModules(new JavaTimeModule(), new GsonModule());
	public static final String ERROR_JSON_KEY = "error";
	public static final String DATA_JSON_KEY = "data";

	public static final String JWT_TOKEN_PREFIX = "Bearer ";

	public static final Duration TOKEN_EXPIRATION_DURATION_NORMAL = Duration.ofHours(4);
	public static final Duration TOKEN_EXPIRATION_DURATION_EXTENDED = Duration.ofDays(7);

	public static final String[] STRING_ARRAY = new String[0];
	public static final List<String> LOW_TIER_ROLES = List.of("LOW");
	public static final List<String> MEDIUM_TIER_ROLES = List.of("LOW", "MEDIUM");
	public static final List<String> TOP_TIER_ROLES = List.of("LOW", "MEDIUM", "TOP");
}

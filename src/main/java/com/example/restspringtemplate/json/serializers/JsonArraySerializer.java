package com.example.restspringtemplate.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonArray;

import java.io.IOException;

/**
 * Serializer for {@link JsonArray}.
 */
public class JsonArraySerializer extends JsonSerializer<JsonArray> {
	@Override
	public void serialize(JsonArray value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartArray();
		JsonTraverser.traverseJsonArray(value, gen);
		gen.writeEndArray();
	}
}

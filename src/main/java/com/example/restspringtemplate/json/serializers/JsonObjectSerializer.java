package com.example.restspringtemplate.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Serializer for {@link JsonObject}.
 */
public class JsonObjectSerializer extends JsonSerializer<JsonObject> {
	@Override
	public void serialize(JsonObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		JsonTraverser.traverseJsonObject(value, gen);
		gen.writeEndObject();
	}
}

package com.example.restspringtemplate.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Utility class to traverse a GSON object and write it to a Jackson generator.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonTraverser {
	/**
	 * Traverse a GSON object and write it to a Jackson generator.
	 *
	 * @param object GSON object to traverse and write.
	 * @param gen    Jackson generator to write to.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	static void traverseJsonObject(JsonObject object, JsonGenerator gen) throws IOException {
		for (var entry : object.entrySet()) {
			gen.writeFieldName(entry.getKey());
			traverseElement(entry.getValue(), gen);
		}
	}

	/**
	 * Traverse a GSON array and write it to a Jackson generator.
	 *
	 * @param array GSON array to traverse and write.
	 * @param gen   Jackson generator to write to.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	static void traverseJsonArray(JsonArray array, JsonGenerator gen) throws IOException {
		for (JsonElement element : array) {
			traverseElement(element, gen);
		}
	}

	/**
	 * Traverse a GSON element and write it to a Jackson generator.
	 *
	 * @param element GSON element to traverse and write.
	 * @param gen     Jackson generator to write to.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	static void traverseElement(JsonElement element, JsonGenerator gen) throws IOException {
		if (element.isJsonPrimitive()) {
			gen.writeObject(element.getAsJsonPrimitive()); // This will call the JsonPrimitiveSerializer
		} else if (element.isJsonObject()) {
			gen.writeStartObject();
			traverseJsonObject(element.getAsJsonObject(), gen); // This will call the JsonObjectSerializer
			gen.writeEndObject();
		} else if (element.isJsonArray()) {
			gen.writeStartArray();
			traverseJsonArray(element.getAsJsonArray(), gen); // This will call the JsonArraySerializer
			gen.writeEndArray();
		} else if (element.isJsonNull()) {
			gen.writeNull();
		}
	}
}

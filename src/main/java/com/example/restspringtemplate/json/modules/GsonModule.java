package com.example.restspringtemplate.json.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.example.restspringtemplate.json.serializers.JsonArraySerializer;
import com.example.restspringtemplate.json.serializers.JsonObjectSerializer;
import com.example.restspringtemplate.json.serializers.JsonPrimitiveSerializer;

/**
 * Jackson module to serialize GSON objects.
 */
public class GsonModule extends SimpleModule {
	public GsonModule() {
		super("GsonModule");

		addSerializer(JsonObject.class, new JsonObjectSerializer());
		addSerializer(JsonPrimitive.class, new JsonPrimitiveSerializer());
		addSerializer(JsonArray.class, new JsonArraySerializer());
	}
}

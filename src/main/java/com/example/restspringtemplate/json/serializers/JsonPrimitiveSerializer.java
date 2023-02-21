package com.example.restspringtemplate.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Serializer for {@link JsonPrimitive}.
 */
public class JsonPrimitiveSerializer extends JsonSerializer<JsonPrimitive> {
	@Override
	public void serialize(JsonPrimitive value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value.isBoolean()) {
			gen.writeBoolean(value.getAsBoolean());
		} else if (value.isString()) {
			gen.writeString(value.getAsString());
		} else if (value.isJsonNull()) {
			gen.writeNull();
		} else if (value.isNumber()) {
			Number number = value.getAsNumber();

			if (number instanceof Integer) {
				gen.writeNumber(number.intValue());
			} else if (number instanceof Long) {
				gen.writeNumber(number.longValue());
			} else if (number instanceof Float) {
				gen.writeNumber(number.floatValue());
			} else if (number instanceof Double) {
				gen.writeNumber(number.doubleValue());
			} else if (number instanceof Byte) {
				gen.writeNumber(number.byteValue());
			} else if (number instanceof Short) {
				gen.writeNumber(number.shortValue());
			} else if (number instanceof BigInteger num) {
				gen.writeNumber(num);
			} else if (number instanceof BigDecimal num) {
				gen.writeNumber(num);
			} else {
				// Can be a LazilyParsedNumber or something else. We know it's a number, so we'll
				// just convert it to a long and write it out.
				gen.writeNumber(number.longValue());
			}
		} else {
			throw new IOException("Unknown JsonPrimitive type");
		}
	}
}

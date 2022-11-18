package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

	private static ObjectMapper myObjectMapper = defaultObjectMapper();

	private static ObjectMapper defaultObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}

	public static JsonNode parse(String jsonSrc) throws IOException {
		return myObjectMapper.readTree(jsonSrc);
	}

	public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
		return myObjectMapper.treeToValue(node, clazz);
	}

	public static JsonNode toJson(Object obj) {
		return myObjectMapper.valueToTree(obj);
	}

	public static String stringify(JsonNode node) throws JsonProcessingException {
		return generatejson(node, false);
	}

	public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
		return generatejson(node, true);
	}

	private static String generatejson(Object o, boolean pretty) throws JsonProcessingException {
		ObjectWriter objectWritter = myObjectMapper.writer();
		if (pretty)
			objectWritter = objectWritter.with(SerializationFeature.INDENT_OUTPUT);
		return objectWritter.writeValueAsString(o);
	}

}

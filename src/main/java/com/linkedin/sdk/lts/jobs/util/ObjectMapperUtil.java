package com.linkedin.sdk.lts.jobs.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.linkedin.sdk.lts.jobs.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.jobs.exception.JsonSerializationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Utility class for JSON serialization and deserialization operations using Jackson.
 * This class provides a singleton ObjectMapper instance with standardized configuration
 * for consistent JSON handling throughout the API client.
 */
public class ObjectMapperUtil {

  private static final ObjectMapper objectMapper = createObjectMapper();
  private static final Logger LOGGER = Logger.getLogger(ObjectMapperUtil.class.getName());

  /**
   * Creates and configures an ObjectMapper with standardized settings.
   *
   * @return A configured ObjectMapper instance
   */
  private static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    // Configure serialization settings
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Configure deserialization settings
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return mapper;
  }

  /**
   * Converts an object to its JSON string representation.
   *
   * @param value The object to convert to JSON
   * @return The JSON string representation of the object
   * @throws RuntimeException If serialization fails
   */
  public static String toJson(Object value) throws JsonSerializationException {
    try {
      return objectMapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      String errorMessage = String.format("Failed to serialize object of type %s to JSON: %s",
          value.getClass().getSimpleName(), e.getMessage());
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new JsonSerializationException(errorMessage, e);
    }
  }

  /**
   * Converts a JSON string to an object of the specified class.
   *
   * @param json The JSON string to convert
   * @param clazz The class to convert the JSON to
   * @param <T> The type of the class
   * @return The object deserialized from the JSON string
   * @throws RuntimeException If deserialization fails
   */
  public static <T> T fromJson(String json, Class<T> clazz) throws JsonDeserializationException {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      String errorMessage = String.format("Failed to deserialize JSON to %s: %s",
          clazz.getSimpleName(), e.getMessage());
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new JsonDeserializationException(errorMessage, e);
    }
  }
}
package com.linkedin.sdk.lts.jobs.exception;

/**
 * Exception thrown when a JSON serialization or deserialization error occurs.
 * This exception indicates problems encountered during the process of
 * converting Java objects to JSON format or vice versa.
 *
 * <p>Examples of scenarios where this exception might be thrown:</p>
 * <ul>
 *   <li>Invalid object structure that cannot be serialized</li>
 *   <li>Missing required fields during deserialization</li>
 *   <li>Type mismatches during JSON parsing</li>
 *   <li>Malformed JSON content</li>
 * </ul>
 */
public class JsonSerializationException extends Exception {

  /**
   * Constructs a new JsonSerializationException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the serialization error
   */
  public JsonSerializationException(String message) {
    super(message);
  }

  /**
   * Constructs a new JsonSerializationException with the specified detail message and cause.
   *
   * @param message the detail message explaining the reason for the serialization error
   * @param cause the cause of the serialization error. A null value is permitted
   *              and indicates that the cause is nonexistent or unknown
   */
  public JsonSerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}

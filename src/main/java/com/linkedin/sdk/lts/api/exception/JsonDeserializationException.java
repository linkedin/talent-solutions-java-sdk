package com.linkedin.sdk.lts.api.exception;

/**
 * Exception thrown when a JSON deserialization error occurs.
 * This exception indicates problems encountered during the process of
 * converting JSON content into Java objects.
 *
 * <p>Examples of scenarios where this exception might be thrown:</p>
 * <ul>
 *   <li>Malformed JSON input</li>
 *   <li>Missing or unexpected fields in JSON</li>
 *   <li>Type mismatches when mapping JSON to Java objects</li>
 *   <li>Invalid JSON structure or syntax errors</li>
 * </ul>
 */
public class JsonDeserializationException extends Exception {

  /**
   * Constructs a new JsonDeserializationException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the deserialization error
   */
  public JsonDeserializationException(String message) {
    super(message);
  }

  /**
   * Constructs a new JsonDeserializationException with the specified detail message and cause.
   *
   * @param message the detail message explaining the reason for the deserialization error
   * @param cause the cause of the deserialization error. A null value is permitted
   *              and indicates that the cause is nonexistent or unknown
   */
  public JsonDeserializationException(String message, Throwable cause) {
    super(message, cause);
  }
}

package com.linkedin.sdk.lts.api.model.response.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a generic API response wrapper for LinkedIn Talent Solutions API.
 * This class is used to encapsulate the response data along with additional metadata.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @param <T> The type of the response payload.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

  /**
   * The actual payload of the response.
   */
  private T body;

  /**
   * HTTP status code of the response.
   */
  private int httpStatusCode;

  /**
   * Http status category of the response (e.g., SUCCESS, CLIENT_ERROR, SERVER_ERROR).
   */
  private HttpStatusCategory httpStatusCategory;

  /**
   * Response headers returned by the server.
   */
  private Map<String, List<String>> responseHeaders;
}
package com.linkedin.sdk.lts.api.exception;

import java.util.List;
import java.util.Map;


/**
 * Exception thrown when a transient error occurs while interacting with the LinkedIn API.
 * This is used to indicate that the error is temporary and may be resolved by retrying the request.
 * Handled by the retry mechanism in the LinkedInHttpClient.
 */
public class TransientLinkedInApiException extends LinkedInApiException {

  /**
   * Constructs a new TransientLinkedInApiException with the specified status code, message, and details.
   *
   * @param statusCode the HTTP status code returned by the LinkedIn API
   * @param headers the HTTP headers returned by the LinkedIn API
   * @param details additional details about the error
   */
  public TransientLinkedInApiException(int statusCode, Map<String, List<String>> headers, String details) {
    super(statusCode, headers, details);
  }
}

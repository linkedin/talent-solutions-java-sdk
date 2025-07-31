package com.linkedin.sdk.lts.api.exception;

import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;


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
   * @param message a descriptive message about the error
   * @param details additional details about the error
   */
  public TransientLinkedInApiException(int statusCode, String message, String details) {
    super(statusCode, message, details);
  }
}

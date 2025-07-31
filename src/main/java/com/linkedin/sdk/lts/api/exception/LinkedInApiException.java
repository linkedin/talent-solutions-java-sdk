package com.linkedin.sdk.lts.api.exception;

import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;


/**
 * Exception thrown when an error occurs while interacting with the LinkedIn API.
 * This exception encapsulates the HTTP status code and error body returned by the API,
 * allowing for detailed error handling and logging.
 *
 * <p>Examples of scenarios where this exception might be thrown:</p>
 * <ul>
 *   <li>Invalid request parameters</li>
 *   <li>Authentication failures</li>
 *   <li>Resource not found</li>
 *   <li>Server errors</li>
 * </ul>
 */

public class LinkedInApiException extends Exception {
  private final int statusCode;
  private final String errorBody;

  /**
   * Constructs a new LinkedInApiException with the specified status code and error body.
   *
   * @param statusCode the HTTP status code returned by the LinkedIn API
   * @param errorBody the error body returned by the LinkedIn API, providing details about the error
   */
  public LinkedInApiException(int statusCode, String errorBody, String message) {
    super(message);
    this.statusCode = statusCode;
    this.errorBody = errorBody;
  }

  /**
   * Verify http status code is a transient error and can be retried.
   *
   * @param statusCode the HTTP status code returned by the LinkedIn API
   */
  public static boolean isTransient(int statusCode) {
    return HttpStatusCategory.SERVER_ERROR.matches(statusCode);
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getErrorBody() {
    return errorBody;
  }
}

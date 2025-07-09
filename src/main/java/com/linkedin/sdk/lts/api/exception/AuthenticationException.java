package com.linkedin.sdk.lts.api.exception;

/**
 * Exception thrown when authentication fails in the LinkedIn SDK.
 * This exception indicates problems with the authentication process,
 * such as invalid credentials, expired tokens, or network issues
 * during authentication.
 *
 * <p>Examples of scenarios where this exception might be thrown:</p>
 * <ul>
 *   <li>Invalid client credentials</li>
 *   <li>Network connectivity issues during token retrieval</li>
 *   <li>Invalid or expired access tokens</li>
 *   <li>OAuth 2.0 server errors</li>
 * </ul>
 */
public class AuthenticationException extends Exception {

  /**
   * Constructs a new AuthenticationException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the authentication failure
   */
  public AuthenticationException(String message) {
    super(message);
  }

  /**
   * Constructs a new AuthenticationException with the specified detail message and cause.
   *
   * @param message the detail message explaining the reason for the authentication failure
   * @param cause the cause of the authentication failure. A null value is permitted
   *        and indicates that the cause is nonexistent or unknown
   */
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

}

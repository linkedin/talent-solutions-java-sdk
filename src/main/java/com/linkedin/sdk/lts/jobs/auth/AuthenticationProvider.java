package com.linkedin.sdk.lts.jobs.auth;

import com.linkedin.sdk.lts.jobs.exception.AuthenticationException;

/**
 * Provides authentication capabilities for the LinkedIn SDK.
 * This interface defines the contract for obtaining and validating access tokens
 * used for authenticating requests to LinkedIn's APIs.
 *
 * <p>Implementations of this interface should handle the lifecycle of access tokens,
 * including obtaining new tokens when necessary and validating existing ones.
 * They may implement different authentication flows (e.g., OAuth 2.0, Client Credentials)
 * depending on the use case.</p>
 *
 * <p>Thread-safety requirements should be documented by implementing classes.</p>
 */
public interface AuthenticationProvider {

  /**
   * Retrieves a valid access token for authenticating API requests.
   *
   * <p>If the current token is invalid or expired, implementations should
   * obtain a new token before returning. The specific mechanism for obtaining
   * new tokens depends on the implementation.</p>
   *
   * @return a valid access token as a String
   * @throws AuthenticationException if unable to obtain a valid access token
   *         due to network issues, invalid credentials, or other authentication failures
   */
  String getAccessToken() throws AuthenticationException;

  /**
   * Checks if the current access token is valid.
   *
   * <p>This method should verify both the existence and validity of the token,
   * including expiration status if applicable.</p>
   *
   * @return true if a valid access token exists, false otherwise
   */
  boolean isTokenValid();

}

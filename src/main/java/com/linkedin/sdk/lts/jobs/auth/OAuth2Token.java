package com.linkedin.sdk.lts.jobs.auth;

import java.time.Instant;

/**
 * Represents an OAuth 2.0 access token with associated expiration time.
 * This class provides immutable storage of the token and handles expiration logic.
 *
 * <p>The token is considered expired 30 seconds before its actual expiration time
 * to provide a safety buffer for ongoing operations. This helps prevent failures
 * in cases where the token might expire during a request.</p>
 *
 * <p>This class is immutable and thread-safe.</p>
 */
public class OAuth2Token {

  /**
   * The OAuth 2.0 access token string.
   */
  private final String accessToken;

  /**
   * The absolute expiration time in milliseconds since epoch.
   * This is calculated as the current time plus the expiration duration.
   */
  private final long expirationTime;

  /**
   * Time buffer in milliseconds before the actual expiration time.
   */
  private static final long EXPIRATION_BUFFER_MILLIS = 30000;

  /**
   * Constant for converting seconds to milliseconds.
   */
  private static final long MILLIS_PER_SECOND = 1000;


  /**
   * Creates a new OAuth2Token with the given access token and expiration duration.
   *
   * @param accessToken the OAuth 2.0 access token string
   * @param expiresIn the number of seconds until the token expires
   * @throws IllegalArgumentException if accessToken is null or expiresIn is negative
   */
  public OAuth2Token(String accessToken, long expiresIn) {
    this.accessToken = accessToken;
    this.expirationTime = Instant.now().toEpochMilli() + (expiresIn * MILLIS_PER_SECOND);
  }

  /**
   * Returns the access token string.
   *
   * @return the OAuth 2.0 access token
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * Checks if the token is expired or about to expire.
   *
   * <p>The token is considered expired if there are less than 30 seconds
   * remaining until its actual expiration time. This buffer helps prevent
   * failures in cases where the token might expire during a request.</p>
   *
   * @return true if the token is expired or will expire within 30 seconds,
   *         false otherwise
   */
  public boolean isExpired() {
    return Instant.now().toEpochMilli() > (expirationTime - EXPIRATION_BUFFER_MILLIS);
  }
}
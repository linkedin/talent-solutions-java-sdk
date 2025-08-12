package com.linkedin.sdk.lts.internal.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


/**
 * Configuration class for OAuth 2.0 client credentials flow authentication.
 * Contains the essential parameters needed to authenticate with LinkedIn's OAuth 2.0 endpoints.
 *
 * @see lombok.Builder
 * @see lombok.Data
 */
@Data
@Builder
@AllArgsConstructor
public class OAuth2Config {

  /**
   * The client ID issued by LinkedIn for your application.
   * This value is used to identify your application when making OAuth requests.
   * Can be obtained from the LinkedIn Developer Portal.
   */
  @NonNull
  private final String clientId;

  /**
   * The client secret issued by LinkedIn for your application.
   * This value is used to authenticate your application when making OAuth requests.
   * Should be kept secure and not exposed in client-side code.
   */
  @NonNull
  private final String clientSecret;

  /**
   * The parent client ID for the application.
   * This is used to identify the parent application in multi-application scenarios.
   */
  @NonNull
  private final String parentClientId;

  /**
   * The URL of the OAuth 2.0 token endpoint.
   * This is the endpoint where the application will make requests to obtain access tokens.
   * For LinkedIn, this is typically "https://www.linkedin.com/oauth/v2/accessToken".
   */
  @NonNull
  private final String tokenUrl;

}

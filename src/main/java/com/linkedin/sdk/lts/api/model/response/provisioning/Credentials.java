package com.linkedin.sdk.lts.api.model.response.provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents the OAuth 2.0 credentials for the application.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04">LinkedIn Provisioning API Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

  /**
   * Client ID value used during OAuth 2.0 token request flow.
   */
  @JsonProperty("client_id")
  private String clientId;

  /**
   * Client Secret value used during OAuth 2.0 token request flow.
   * May be up to 256 characters in length and contain non-alphanumeric characters.
   */
  @JsonProperty("client_secret")
  private String clientSecret;
}
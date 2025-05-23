package com.linkedin.sdk.lts.jobs.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represent TokenInfo information.
 * Information to be used for LinkedIn API authentication.
 *
 * @see <a herf="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-module1-basics?view=li-lts-2025-01#step-2-generate-an-access-token"></a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

  /**
   * Token to use to authenticate with LinkedIn API
   */
  @JsonProperty("access_token")
  private String accessToken;

  /**
   * Token expiry time in seconds
   */
  @JsonProperty("expires_in")
  private Long expiresIn;
}

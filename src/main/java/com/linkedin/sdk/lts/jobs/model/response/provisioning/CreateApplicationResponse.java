package com.linkedin.sdk.lts.jobs.model.response.provisioning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response received after creating a child application through LinkedIn's Application Management API.
 *
 * <p>This class contains the application key (URN) and OAuth 2.0 credentials necessary for authentication.</p>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04">LinkedIn Provisioning API Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationResponse {

  /**
   * URN representing the created application.
   */
  private String key;

  /**
   * OAuth 2.0 credentials for the created application.
   */
  private Credentials credentials;
}
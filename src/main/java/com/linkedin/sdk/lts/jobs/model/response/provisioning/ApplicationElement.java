package com.linkedin.sdk.lts.jobs.model.response.provisioning;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a child application in LinkedIn's Application Management API.
 * This class contains the application's credentials, unique identifier, name, description,
 * authorized JavaScript SDK domains, URN, and OAuth 2.0 redirect URLs.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04">LinkedIn Provisioning API Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationElement {

  /**
   * OAuth 2.0 credentials for the application.
   */
  private Credentials credentials;

  /**
   * Unique identifier for the child application from the parent application's perspective.
   */
  private String uniqueForeignId;

  /**
   * Display name of the application.
   */
  private String name;

  /**
   * Description of the application.
   */
  private String description;

  /**
   * List of authorized JavaScript SDK domain names.
   */
  private List<String> validJsSdkDomains;

  /**
   * URN representing the application.
   */
  private String key;

  /**
   * List of authorized OAuth 2.0 redirect URLs.
   */
  private List<String> oauth2AuthorizedCallbackUrls;
}
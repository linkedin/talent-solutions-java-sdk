package com.linkedin.sdk.lts.jobs.model.request.provisioning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the request payload for creating a child application in LinkedIn's Application Management API.
 *
 * <p>This class contains the necessary fields to create a new child application, including required
 * identifiers, display information, and optional OAuth2 and JavaScript SDK configurations.</p>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04">LinkedIn Provisioning API Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationRequest {

  /**
   * A unique identifier for the child application from the parent application's perspective.
   * Must be unique across all child applications belonging to the parent.
   */
  private String uniqueForeignId;

  /**
   * Display name of the child application, shown in OAuth 2.0 authorization dialogs.
   * Format must be "{ATS Name} - {Customer Name}" and cannot exceed 50 characters.
   */
  private String name;

  /**
   * Brief description of the child application for LinkedIn administration purposes.
   */
  private String description;

  /**
   * List of authorized OAuth 2.0 redirect URLs for the application.
   */
  private List<String> oauth2AuthorizedCallbackUrls;

  /**
   * List of authorized JavaScript SDK domain names.
   */
  private List<String> validJsSdkDomains;
}
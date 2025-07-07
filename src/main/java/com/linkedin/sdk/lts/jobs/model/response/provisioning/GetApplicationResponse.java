package com.linkedin.sdk.lts.jobs.model.response.provisioning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the response received when retrieving application details through LinkedIn's Application Management API.
 *
 * <p>This class contains a list of application elements, where each element represents a child application
 * with its configuration and credentials.</p>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04">LinkedIn Provisioning API Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationResponse {

  /**
   * List of application elements returned in the response.
   */
  private List<ApplicationElement> elements;
}
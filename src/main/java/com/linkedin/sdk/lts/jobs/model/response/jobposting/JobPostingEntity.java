package com.linkedin.sdk.lts.jobs.model.response.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a simple job posting entity within the LinkedIn Talent Solutions API.
 * This class is used to identify a job posting by its external ID.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/sync-job-posting?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingEntity {

  /**
   * Unique job posting id within the partner's system.
   * This identifier is used to track and reference the job posting throughout the LinkedIn ecosystem.
   * Required field.
   */
  private String externalJobPostingId;
}
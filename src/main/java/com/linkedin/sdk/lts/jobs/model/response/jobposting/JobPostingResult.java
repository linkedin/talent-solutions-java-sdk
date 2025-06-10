package com.linkedin.sdk.lts.jobs.model.response.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response for a simple job posting operation.
 * This class provides details about the result of a job posting operation as returned by the LinkedIn Talent Solutions API.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/sync-job-posting?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingResult {

  /**
   * The location URI of the created job posting.
   * This field contains the path to the job posting resource.
   */
  private String location;

  /**
   * The unique identifier for the job posting task.
   * This is in URN format and can be used for subsequent API calls.
   */
  private String id;

  /**
   * Contains information about the job posting entity.
   */
  private JobPostingEntity entity;

  /**
   * HTTP status code of the response.
   * Typically 202 for accepted job posting requests.
   */
  private int status;

  /**
   * Detailed information on error if any
   */
  private JobPostingError jobPostingError;
}
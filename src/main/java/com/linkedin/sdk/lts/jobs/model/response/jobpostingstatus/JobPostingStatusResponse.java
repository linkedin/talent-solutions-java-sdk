package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response from LinkedIn's Job Posting Status API.
 * This class corresponds to the response structure defined in LinkedIn's Talent Solutions API
 * for checking the status of job postings.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04">LinkedIn Job Posting Status Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingStatusResponse {

  /**
   * Map of job posting URNs to their respective details.
   * Each key is a job posting URN (e.g., "urn:li:jobPosting:1234567890"),
   * and each value contains details about that job posting.
   */
  private Map<String, JobPostingStatusField> results;

  /**
   * Map containing status information about the API request.
   * This is typically empty when the request succeeds.
   */
  private Map<String, Object> statuses;

  /**
   * Map containing error information about the API request.
   * This is typically empty when the request succeeds.
   */
  private Map<String, Object> errors;
}


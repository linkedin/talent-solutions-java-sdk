package com.linkedin.sdk.lts.api.model.response.jobposting;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response from LinkedIn's Job Posting API after submitting job postings.
 * This class corresponds to the response structure defined in LinkedIn's Talent Solutions API.
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
public class JobPostingResponse {

  /**
   * List of job posting results returned from LinkedIn's API.
   * Each element corresponds to a job posting that was submitted in the request.
   */
  private List<JobPostingResult> elements;
}

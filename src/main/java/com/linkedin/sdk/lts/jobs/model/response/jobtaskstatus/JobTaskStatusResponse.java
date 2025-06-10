package com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response from LinkedIn's Job Task Status API.
 * This class corresponds to the response structure defined in LinkedIn's Talent Solutions API
 * for checking the status of job posting tasks.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href = "https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-taskstatus?view=li-lts-2025-04">LinkedIn Job Task Status Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobTaskStatusResponse {

  /**
   * Map of task URNs to their respective task result details.
   * Each key is a task URN (e.g., "urn:li:simpleJobPostingTask:abc-123"),
   * and each value contains details about that task.
   */
  private Map<String, JobPostingTaskResult> results;

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
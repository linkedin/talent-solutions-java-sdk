package com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the result of a single job posting operation in LinkedIn's Job Posting API.
 * This class captures either the successful outcome (a job URN) or error details.
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
public class JobPostingTaskResult {

  /**
   * The input location for a job using alternateLocations or customer based multiple location job rules.
   */
  private String location;

  /**
   * Status of the task. List of possible statuses: IN_PROGRESS, SUCCEEDED, FAILED, PROCESSED.
   */
  private JobTaskStatus status;

  /**
   * Unique ID for a simple job posting task.
   */
  private String id;

  /**
   * Unique job posting id within the partner's system.
   */
  private String externalJobPostingId;

  /**
   * Job posting resulting from a successful task.
   */
  private String jobPosting;

  /**
   * Description of an error resulting from a failed task.
   */
  private String errorMessage;

}

package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents detailed status information for a LinkedIn job posting.
 * This class provides additional error information when there are issues with a job posting.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04#jobpostingstatusdetail-field-schema">LinkedIn Job Posting Status Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingStatusDetail {

  /**
   * Error code identifier.
   * This field is optional and only present when there is an error.
   */
  private Integer errorCode;

  /**
   * Error description type.
   * This field is optional and only present when there is an error.
   */
  private JobPostingStatusDetailErrorType errorType;

  /**
   * Description of the status.
   * This field is required.
   */
  private String statusMessage;
}

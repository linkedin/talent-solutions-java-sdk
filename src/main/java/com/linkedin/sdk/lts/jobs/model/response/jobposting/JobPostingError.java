package com.linkedin.sdk.lts.jobs.model.response.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an error response from the LinkedIn Talent Solutions API.
 * This class encapsulates details about errors that may occur during job posting operations.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/sync-job-postings?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingError {

  /**
   * Error code that identifies the type of error.
   * This is a string constant like "ERROR_INVALID_LISTED_AT_FIELD".
   */
  private String code;

  /**
   * Numeric service error code associated with the error.
   * Can be used for programmatic error handling.
   */
  private int serviceErrorCode;

  /**
   * The ID of the request that generated the error.
   * Typically contains the job ID or a system-generated request identifier.
   */
  private String requestId;

  /**
   * The fully qualified class name of the error details type.
   * Usually "com.linkedin.restli.common.ErrorDetails".
   */
  private String errorDetailType;

  /**
   * Human-readable error message describing what went wrong.
   * This can be shown to users or logged for troubleshooting.
   */
  private String message;

  /**
   * HTTP status code associated with the error.
   * Common values include 400 (Bad Request), 401 (Unauthorized),
   * 403 (Forbidden), and 500 (Internal Server Error).
   */
  private int status;

  /**
   * Contains additional details about the error.
   * May include the affected job ID or specific fields that caused the error.
   */
  private JobPostingErrorDetail errorDetails;


}

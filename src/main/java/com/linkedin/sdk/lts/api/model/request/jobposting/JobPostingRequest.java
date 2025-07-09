package com.linkedin.sdk.lts.api.model.request.jobposting;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a request wrapper for submitting job postings to LinkedIn's Job Posting API.
 * This class encapsulates a batch of job postings that can be submitted in a single request.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingRequest {

  /**
   * List of job postings to be processed in this request.
   * This field is required and cannot be empty.
   * All job postings in the list must have the same {@link JobPostingOperationType}.
   * Each posting in the list must pass all validation rules defined in
   * {@link JobPosting}.
   */
  private List<JobPosting> elements;

}

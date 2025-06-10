package com.linkedin.sdk.lts.jobs.model.request.jobposting;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents the complete compensation package provided by a job poster in the LinkedIn Job Posting API.
 * This class allows specifying multiple types of compensation (e.g., base salary, bonus, stock options)
 * for a single job posting.
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
public class PosterProvidedCompensation {

  /**
   * Array of compensation elements describing the complete compensation package.
   * Required field that cannot be null or empty.
   * Each element must be a valid {@link Compensation} object that specifies
   * compensation type, period, and value range.
   *
   * @see Compensation
   */
  private List<Compensation> compensations;

}

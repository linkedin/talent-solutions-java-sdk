package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents information about a LinkedIn job posting status.
 * This class provides details about a job posting status as returned by the LinkedIn Talent Solutions API.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-01#jobpostinginfo-field-schema">LinkedIn Job Posting Status Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingInfo {

  /**
   * Represents the date the job posting will expire on LinkedIn.
   * Null if the job posting is not listed yet.
   * The date is epoch timestamp in milliseconds (UTC).
   */
  private Long expireAt;

  /**
   * Represents initial date the job posting was listed on LinkedIn.
   * The date is epoch timestamp in milliseconds (UTC).
   */
  private Long listedAt;

  /**
   * Standardized location for the job listing.
   */
  private String location;

  /**
   * Represents alternate locations for a job with multiple locations.
   * This field takes the locations as input (including the one set in the `location` field).
   * This field is null if the job has only one location.
   * Maximum up to seven alternate locations are allowed.
   */
  private List<String> alternateLocations;

  /**
   * Job company details, contains company name and page url.
   */
  private CompanyDetails companyDetails;

  /**
   * The job visibility tag for the job posting.
   * This field states whether a member viewer is eligible to view a job.
   */
  private JobVisibility visibilityTag;
}

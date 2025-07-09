package com.linkedin.sdk.lts.api.model.response.jobpostingstatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents company details for a job posting.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04#companydetails-field-schema">LinkedIn Job Posting Status Documentation</>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetails {

  /**
   * The name of the company.
   */
  private String companyName;

  /**
   * The URL to the company's LinkedIn page.
   */
  private String companyPage;
}

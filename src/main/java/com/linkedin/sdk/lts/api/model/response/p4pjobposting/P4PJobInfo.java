package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.CompanyDetails;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents job posting information in LinkedIn's Pay for Performance (P4P) system.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PJobInfo {

  /**
   * External job posting id (also known as partnerJobCode) provided by the poster.
   */
  private String externalJobPostingId;

  /**
   * LinkedIn job id to uniquely identify the job in the LinkedIn eco-system.
   */
  private Long linkedInJobPostingId;

  /**
   * Time at which the job was closed and no longer accepts any applications.
   * Measured in epoch milliseconds (UTC).
   */
  private Long closeAt;

  /**
   * Job company details. It contains company name and LinkedIn company page url.
   */
  private CompanyDetails companyDetails;

  /**
   * URL to direct applicants to apply for the specified job,
   * if applicants should be routed to an external website.
   */
  private String companyApplyUrl;

  /**
   * Title of the job provided.
   */
  private String jobTitle;
}

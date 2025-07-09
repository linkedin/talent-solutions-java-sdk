package com.linkedin.sdk.lts.api.model.response.jobpostingstatus;

/**
 * Enum representing the possible error types for a LinkedIn Job Posting Status.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04#jobpostingstatusdetail-field-schema">LinkedIn Job Posting Status Documentation</a>
 */
public enum JobPostingStatusDetailErrorType {
  /**
   * Customer inputted data contains errors.
   */
  CUSTOMER_ERROR,

  /**
   * Partner API request contains errors.
   */
  PARTNER_ERROR,

  /**
   * An error occurred on LinkedIn.
   */
  LINKEDIN_ERROR
}

package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

/**
 * Defines requirement levels for questions in a job application.
 * Used to control whether questions are required, optional, or skipped entirely.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/onsite-apply-config-schema?view=li-lts-2025-04">LinkedIn Apply Connect Documentation</a>
 */
public enum QuestionRequirement {
  /**
   * This question is required.
   * An answer must be provided by the applicant in order to complete the job application.
   */
  REQUIRED,

  /**
   * This question is optional.
   * An answer does not need to be provided by the applicant in order to complete the job application.
   */
  OPTIONAL,

  /**
   * This question is not rendered on the job application.
   * No response will be provided for this question.
   */
  SKIP
}
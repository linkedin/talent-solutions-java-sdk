package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible types of job listings in the LinkedIn Job Posting API.
 * These values represent different tiers of job postings with varying
 * levels of visibility, features, and promotion.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
public enum ListingType {
  /**
   * All other job except Premium should use Basic listing type
   */
  BASIC,
  /**
   * Only used for Premium job posting
   */
  PREMIUM
}

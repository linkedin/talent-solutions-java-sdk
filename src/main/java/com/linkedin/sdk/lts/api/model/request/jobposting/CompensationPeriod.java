package com.linkedin.sdk.lts.api.model.request.jobposting;

/**
 * Defines the possible payment periods for compensation in the LinkedIn Job Posting API.
 * These values indicate the frequency at which compensation is provided
 * and are used to clearly communicate payment schedules in job postings.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum CompensationPeriod {
  YEARLY,
  MONTHLY,
  SEMIMONTHLY,
  BIWEEKLY,
  WEEKLY,
  DAILY,
  HOURLY
}

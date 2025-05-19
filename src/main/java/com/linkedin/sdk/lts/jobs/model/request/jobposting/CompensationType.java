package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible types of compensation in the LinkedIn Job Posting API.
 * These values represent different forms of monetary and non-monetary
 * compensation that may be offered as part of a job's total compensation package.
 *
 * TODO: Update documentation for each enum - https://jira01.corp.linkedin.com:8443/browse/JOBS-81461
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
public enum CompensationType {
  BASE_SALARY,
  TIPS,
  COMMISSION,
  PROFIT_SHARING,
  STOCK_OPTIONS,
  STOCK,
  BONUS,
  SIGN_ON_BONUS,
  OVER_TIME,
  OTHER
}

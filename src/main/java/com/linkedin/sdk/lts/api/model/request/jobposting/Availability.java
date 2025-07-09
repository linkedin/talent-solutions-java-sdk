package com.linkedin.sdk.lts.api.model.request.jobposting;

/**
 * Availability enum for JobPosting
 *
 * TODO: Update documentation for each enum - https://jira01.corp.linkedin.com:8443/browse/JOBS-81461
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum Availability {

  /**
   * Represents a job that is available to all users, regardless of their LinkedIn membership status.
   */
  PUBLIC,

  /**
   * Represents a job that is available only to members of the company posting the job.
   */
  INTERNAL;
}

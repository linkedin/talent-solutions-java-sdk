package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible experience levels for job postings in the LinkedIn Job Posting API.
 * These values indicate the level of professional experience and expertise
 * required or expected for a position.
 *
 * TODO: Update documentation for each enum - https://jira01.corp.linkedin.com:8443/browse/JOBS-81461
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum ExperienceLevel {
  ENTRY_LEVEL,
  MID_SENIOR_LEVEL,
  DIRECTOR,
  EXECUTIVE,
  INTERNSHIP,
  ASSOCIATE,
  NOT_APPLICABLE
}

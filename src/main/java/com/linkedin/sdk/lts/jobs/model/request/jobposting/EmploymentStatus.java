package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible employment status types for job postings in the LinkedIn Job Posting API.
 * These values indicate the nature of employment being offered in terms of
 * time commitment and employment relationship.
 *
 * TODO: Update documentation for each enum - https://jira01.corp.linkedin.com:8443/browse/JOBS-81461
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
public enum EmploymentStatus {
  FULL_TIME,
  PART_TIME,
  CONTRACT,
  INTERNSHIP,
  TEMPORARY,
  VOLUNTEER
}

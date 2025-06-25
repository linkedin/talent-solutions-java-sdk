package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible employment status types for job postings in the LinkedIn Job Posting API.
 * These values indicate the nature of employment being offered in terms of
 * time commitment and employment relationship.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum EmploymentStatus {

  /**
   * Represents full-time employment, typically involving a standard workweek
   * of 35-40 hours or more.
   */
  FULL_TIME,

  /**
   * Represents part-time employment, typically involving fewer hours than full-time
   * positions, often less than 35 hours per week.
   */
  PART_TIME,

  /**
   * Represents contract-based employment, where the individual is hired for a specific
   * project or period, often with a defined scope of work and duration.
   */
  CONTRACT,

  /**
   * Represents internship positions, which are typically temporary roles
   */
  INTERNSHIP,

  /**
   * Represents temporary employment, where the individual is hired for a short-term
   * assignment or to fill in for a regular employee who is absent.
   */
  TEMPORARY,

  /**
   * Represents volunteer work, where the individual offers their services without
   * financial compensation, often for a charitable or community cause.
   */
  VOLUNTEER
}

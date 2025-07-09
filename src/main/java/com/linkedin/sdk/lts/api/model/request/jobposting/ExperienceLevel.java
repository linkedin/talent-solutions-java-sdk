package com.linkedin.sdk.lts.api.model.request.jobposting;

/**
 * Defines the possible experience levels for job postings in the LinkedIn Job Posting API.
 * These values indicate the level of professional experience and expertise
 * required or expected for a position.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum ExperienceLevel {

  /**
   * Represents entry-level positions, typically requiring little to no professional experience.
   */
  ENTRY_LEVEL,

  /**
   * Represents positions that require some professional experience, usually more than entry-level.
   */
  MID_SENIOR_LEVEL,

  /**
   * Represents positions that require significant professional experience, often in leadership or specialized roles.
   */
  DIRECTOR,

  /**
   * Represents positions that require extensive professional experience, often in executive roles.
   */
  EXECUTIVE,

  /**
   * Represents positions that are typically internships, often for students or recent graduates.
   */
  INTERNSHIP,

  /**
   * Represents positions that are typically for individuals with some level of professional experience,
   * but not necessarily at the level of mid-senior or higher.
   */
  ASSOCIATE,

  /**
   * Represents positions where the experience level is not applicable or not specified.
   * This can be used when the job posting does not require a specific level of experience.
   */
  NOT_APPLICABLE
}

package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the possible visibility settings for a LinkedIn job posting.
 * These values control who can see the job posting on LinkedIn.
 *
 * <p>Visibility values based on LinkedIn documentation:</p>
 * <ul>
 *   <li>PRIVATE - Job visibility is restricted across job search. Job cannot be accessed with explicit LinkedIn job URL.</li>
 *   <li>TEST - Job is visible to LinkedIn allow listed employees only.</li>
 *   <li>PARTNER - Jobs are only served to a group of allow listed partner developers. Partners can see each others jobs abiding by the product access rules.</li>
 *   <li>COMPANY - Job is only visible to the company employees to which the job is associated. If a job is associated with a specific company & the member is an employee of a different company, then the member cannot access to view the job.</li>
 *   <li>PUBLIC - By default job will be visible across the platform in all channels.</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04">LinkedIn Job Posting Status Documentation</a>
 */
public enum JobVisibility {

  /**
   * Job visibility is restricted across job search.
   * Job cannot be accessed with explicit LinkedIn job URL.
   */
  PRIVATE,

  /**
   * Job is visible to LinkedIn allow listed employees only.
   */
  TEST,

  /**
   * Jobs are only served to a group of allow listed partner developers.
   * Partners can see each others jobs abiding by the product access rules.
   */
  PARTNER,

  /**
   * Job is only visible to the company employees to which the job is associated.
   * If a job is associated with a specific company & the member is an employee of a different company,
   * then the member cannot access to view the job.
   */
  COMPANY,

  /**
   * By default job will be visible across the platform in all channels.
   */
  PUBLIC,

  /**
   * The status is unknown.
   * This is used when the status value from the API doesn't match any known value.
   */
  UNKNOWN;

  /**
   * Deserializes a string value to the corresponding PromotionStatus enum value.
   * If the string doesn't match any known status, UNKNOWN is returned.
   *
   * @param value the string status value
   * @return the corresponding PromotionStatus enum value
   */
  @JsonCreator
  public static JobVisibility fromString(String value) {
    if (value == null) {
      return UNKNOWN;
    }

    try {
      return valueOf(value);
    } catch (IllegalArgumentException e) {
      return UNKNOWN;
    }
  }

  /**
   * Serializes the enum value to a string.
   *
   * @return the string representation of this enum value
   */
  @JsonValue
  public String toValue() {
    return this.name();
  }
}

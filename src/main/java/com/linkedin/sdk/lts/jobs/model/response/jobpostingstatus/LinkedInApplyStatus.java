package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the LinkedIn Apply status of a job posting.
 * Uses Jackson annotations to handle serialization/deserialization.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04">LinkedIn Job Posting Status Documentation</a>
 */
public enum LinkedInApplyStatus {
  /**
   * LinkedIn Apply is enabled for the job posting.
   * Candidates can apply directly through LinkedIn.
   */
  ENABLED,

  /**
   * LinkedIn Apply is not enabled for the job posting.
   * Candidates cannot apply through LinkedIn.
   */
  NOT_ENABLED,

  /**
   * LinkedIn Apply enablement is in progress.
   * The feature is being set up but is not yet fully enabled.
   */
  IN_PROGRESS,

  /**
   * The status is unknown.
   * This is used when the status value from the API doesn't match any known value.
   */
  UNKNOWN;

  /**
   * Deserializes a string value to the corresponding LinkedInApplyStatus enum value.
   * If the string doesn't match any known status, UNKNOWN is returned.
   *
   * @param value the string status value
   * @return the corresponding LinkedInApplyStatus enum value
   */
  @JsonCreator
  public static LinkedInApplyStatus fromString(String value) {
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
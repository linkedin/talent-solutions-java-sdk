package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the listing state of a job posting.
 * Uses Jackson annotations to handle serialization/deserialization.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-01">LinkedIn Job Posting Status Documentation</a>
 */
public enum ListingStatus {
  /**
   * The job posting is actively listed on LinkedIn.
   * The job is visible to candidates on the platform.
   */
  LISTED,

  /**
   * The job posting is not listed on LinkedIn.
   * The job is not visible to candidates on the platform.
   */
  NOT_LISTED,

  /**
   * The listing process is in progress.
   * The job is being evaluated and processed for listing.
   */
  IN_PROGRESS,

  /**
   * The listing status is unknown.
   * This is used when the status value from the API doesn't match any known value.
   */
  UNKNOWN;

  /**
   * Deserializes a string value to the corresponding ListingStatus enum value.
   * If the string doesn't match any known status, UNKNOWN is returned.
   *
   * @param value the string status value
   * @return the corresponding ListingStatus enum value
   */
  @JsonCreator
  public static ListingStatus fromString(String value) {
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
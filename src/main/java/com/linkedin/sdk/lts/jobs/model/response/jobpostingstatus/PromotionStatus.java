package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the promotion status of a job listing.
 * Uses Jackson annotations to handle serialization/deserialization.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04">LinkedIn Job Posting Status Documentation</a>
 */
public enum PromotionStatus {
  /**
   * The job listing is promoted on LinkedIn.
   * Promoted jobs receive enhanced visibility and reach on the platform.
   */
  PROMOTED,

  /**
   * The job listing is not promoted on LinkedIn.
   * The job receives standard visibility without promotional enhancements.
   */
  NOT_PROMOTED,

  /**
   * The promotion status is unknown.
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
  public static PromotionStatus fromString(String value) {
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
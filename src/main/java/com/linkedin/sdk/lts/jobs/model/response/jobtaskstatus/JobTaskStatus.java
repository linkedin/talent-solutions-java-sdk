package com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Enum representing the possible status values for a job posting task status.
 * Uses Jackson annotations to handle serialization/deserialization.
 *
 * <a href = "https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-taskstatus?view=li-lts-2025-04">LinkedIn Job Task Status Documentation</a>
 */
public enum JobTaskStatus {
  /**
   * This is a temporary state and the job is still being evaluated
   */
  IN_PROGRESS,

  /**
   * For a CREATE operation, API job was posted.
   * For UPDATE operation, update was successful and API job is posted on LinkedIn.
   * For CLOSE or RENEW operation, close or renew request was successful
   */
  SUCCEEDED,

  /**
   * For a CREATE operation, API job was not posted or did not pass validation.
   * For UPDATE operation, update was not successful. See task status on most recent CREATE operation to determine if the API job was posted on LinkedIn.
   * For CLOSE or RENEW operation, close or renew request was not successful
   */
  FAILED,

  /**
   * The API job has been processed successfully by LinkedIn
   */
  PROCESSED,

  /**
   * The status of the job posting is unknown.
   * This is used when the status value from the API doesn't match any known value.
   */
  UNKNOWN;

  /**
   * Deserializes a string value to the corresponding JobTaskStatus enum value.
   * If the string doesn't match any known status, UNKNOWN is returned.
   *
   * @param value the string status value
   * @return the corresponding JobTaskStatus enum value
   */
  @JsonCreator
  public static JobTaskStatus fromString(String value) {
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

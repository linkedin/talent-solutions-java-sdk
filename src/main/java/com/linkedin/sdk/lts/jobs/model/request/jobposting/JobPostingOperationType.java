package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible operations that can be performed on job postings
 * in the LinkedIn Job Posting API. This enum provides type-safe operation
 * types and string value conversion utilities.
 *
 * <p>Each operation type has an associated string value that matches
 * the expected API payload format. The enum provides methods to convert
 * between enum constants and their string representations.</p>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum JobPostingOperationType {

  /**
   * Represents the creation of a new job posting.
   */
  CREATE("CREATE"),

  /**
   * Represents an update to an existing job posting.
   */
  UPDATE("UPDATE"),

  /**
   * Represents the renewal of an existing job posting, typically extending its visibility or validity.
   */
  RENEW("RENEW"),

  /**
   * Represents the closure of a job posting, indicating that it is no longer active or accepting applications.
   */
  CLOSE("CLOSE"),

  /**
   * Represents an upgrade operation on a job posting, which may involve enhancing its features or visibility.
   */
  UPGRADE("UPGRADE"),

  /**
   * Represents a downgrade operation on a job posting, which may involve reducing its features or visibility.
   */
  DOWNGRADE("DOWNGRADE");

  private final String value;

  JobPostingOperationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  // Method to get enum by string value
  public static JobPostingOperationType fromValue(String value) {
    for (JobPostingOperationType type : JobPostingOperationType.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown value: " + value);
  }
}
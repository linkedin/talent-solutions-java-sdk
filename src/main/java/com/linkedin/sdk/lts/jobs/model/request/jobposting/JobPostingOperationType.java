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
 * TODO: Update documentation for each enum - https://jira01.corp.linkedin.com:8443/browse/JOBS-81461
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
public enum JobPostingOperationType {
  CREATE("CREATE"),
  UPDATE("UPDATE"),
  RENEW("RENEW"),
  CLOSE("CLOSE"),
  UPGRADE("UPGRADE"),
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
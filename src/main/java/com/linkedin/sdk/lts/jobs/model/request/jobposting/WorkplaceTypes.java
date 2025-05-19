package com.linkedin.sdk.lts.jobs.model.request.jobposting;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines the possible workplace arrangements for job postings in the LinkedIn Job Posting API.
 * These values indicate where the work will be performed and the expected
 * physical presence requirements for the position.
 *
 * <p>Each workplace type has an associated display string value. The enum provides
 * methods to convert between enum constants and their string representations.</p>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
public enum WorkplaceTypes {
  /**
   * On-Site should be used when it is mandatory for employee to work only from the designated office.
   */
  ON_SITE("On-site"),
  /**
   * Hybrid should be provided if the employee is expected to work some days from home and some days from the designated office.
   */
  HYBRID("Hybrid"),
  /**
   * Remote value should be provided when employee is allowed to work remotely.
   */
  REMOTE("Remote");

  private final String value;

  WorkplaceTypes(String value) {
    this.value = value;
  }

  @JsonValue  // This tells Jackson to use this value when serializing
  public String getValue() {
    return value;
  }

}
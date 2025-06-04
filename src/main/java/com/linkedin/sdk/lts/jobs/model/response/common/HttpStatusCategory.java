package com.linkedin.sdk.lts.jobs.model.response.common;

/**
 * Enum representing the categories of HTTP status codes.
 * Each category corresponds to a range of status codes.
 * <p>
 * - INFORMATIONAL: 100-199
 * - SUCCESS: 200-299
 * - REDIRECTION: 300-399
 * - CLIENT_ERROR: 400-499
 * - SERVER_ERROR: 500-599
 * - UNKNOWN: 600 and above
 */
public enum HttpStatusCategory {

  /**
   * Informational responses (100-199)
   */
  INFORMATIONAL(100, 199),

  /**
   * Successful responses (200-299)
   */
  SUCCESS(200, 299),

  /**
   * Redirection messages (300-399)
   */
  REDIRECTION(300, 399),

  /**
   * Client error responses (400-499)
   */
  CLIENT_ERROR(400, 499),

  /**
   * Server error responses (500-599)
   */
  SERVER_ERROR(500, 599),

  /**
   * Unknown status codes (600 and above)
   */
  UNKNOWN(600, Integer.MAX_VALUE);

  private final int min;
  private final int max;

  HttpStatusCategory(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public boolean matches(int code) {
    return code >= min && code <= max;
  }

  public static HttpStatusCategory fromCode(int code) {
    for (HttpStatusCategory category : values()) {
      if (category.matches(code)) {
        return category;
      }
    }
    return UNKNOWN;
  }

  public int getDefaultCode() {
    return min;  // Returns 400 for CLIENT_ERROR, 500 for SERVER_ERROR, etc.
  }
}
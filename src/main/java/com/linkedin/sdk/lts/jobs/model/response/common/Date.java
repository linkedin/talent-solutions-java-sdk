package com.linkedin.sdk.lts.jobs.model.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Date Schema across LinkedIn's APIs.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/limited-pay-for-performance/lp4p-reports?context=linkedin%2Ftalent%2Flimited-pay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#shared-api-response-schema">LinkedIn Date Object Schema</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Date {
  /**
   * The month (1-12).
   */
  private Integer month;

  /**
   * The year (e.g., 2025).
   */
  private Integer year;

  /**
   * The day of the month (1-31).
   */
  private Integer day;
}
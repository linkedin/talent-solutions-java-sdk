package com.linkedin.sdk.lts.jobs.model.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Common Date Range Schema across LinkedIn's APIs.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/limited-pay-for-performance/lp4p-reports?context=linkedin%2Ftalent%2Flimited-pay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#daterange-object-schema">LinkedIn Date Range Schema</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {

  /**
   * The start date of the range (Inclusive).
   */
  private Date start;

  /**
   * The end date of the range (Inclusive).
   */
  private Date end;
}

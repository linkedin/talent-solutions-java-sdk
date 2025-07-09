package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import com.linkedin.sdk.lts.api.model.request.jobposting.MoneyAmount;
import com.linkedin.sdk.lts.api.model.response.common.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the daily performance metrics for a job posting in LinkedIn's Pay for Performance (LP4P) system.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PJobPerformanceMetrics {

  /**
   * The date of the metrics record.
   */
  private Date date;

  /**
   * The charge amount for the job posting on this date.
   */
  private MoneyAmount charge;

  /**
   * Count of apply clicks the job posting received on this date.
   */
  private Integer applyClickCount;

  /**
   * Count of views the job posting received on this date.
   */
  private Integer viewCount;
}

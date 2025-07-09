package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a report for a job posting in the Pay for Performance (P4P) system.
 * Contains job posting information and performance metrics.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PJobReport {

  /**
   * Contains information about the job posting.
   */
  private P4PJobInfo jobPostingInfo;

  /**
   * Contains budget information for the job posting.
   * This includes lifetime budget spent and lifetime budget limit.
   */
  private P4PPartnerReportJobBudgetInfo lifetimeJobBudgetInfo;

  /**
   * Contains performance metrics for the job posting.
   * This includes daily metrics such as charge amount, apply clicks, and view counts.
   */
  private List<P4PJobPerformanceMetrics> performanceMetrics;
}

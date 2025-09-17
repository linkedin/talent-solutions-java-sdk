package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import com.linkedin.sdk.lts.api.model.request.jobposting.MoneyAmount;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents budget information for a partner report job.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PPartnerReportJobBudgetInfo {

  /**
   * Accumulated monetary charges of the job.
   */
  private MoneyAmount serviceTermBudgetSpent;

  /**
   * Total budget limit of the job.
   */
  private MoneyAmount serviceTermBudgetLimit;
}
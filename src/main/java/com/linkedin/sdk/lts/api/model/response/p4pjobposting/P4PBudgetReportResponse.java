package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents service term budget information for a partner.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PBudgetReportResponse {

  /**
   * Service term start timestamp (epoch milliseconds).
   */
  private Long serviceTermStartAt;

  /**
   * Service term end timestamp (epoch milliseconds).
   */
  private Long serviceTermEndAt;

  /**
   * List of partner budget details for the service term.
   */
  private List<P4PPartnerReportJobBudgetInfo> partnerBudgetDetails;
}
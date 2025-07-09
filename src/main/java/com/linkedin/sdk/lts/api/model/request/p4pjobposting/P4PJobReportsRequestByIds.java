package com.linkedin.sdk.lts.api.model.request.p4pjobposting;

import com.linkedin.sdk.lts.api.model.response.common.DateRange;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Request schema for fetching P4P job reports by external job IDs or partnerJobCode.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#check-job-reports-by-ids">LinkedIn P4P Reports Documentation</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P4PJobReportsRequestByIds {

  /**
   * List of unique external partner job identifiers (e.g., externalJobPostingId or partnerJobCode).
   * Maximum 10 per request.
   */
  private List<String> ids;

  /**
   * Date range to fetch reports (inclusive).
   */
  private DateRange dateRange;

  /**
   * Optional: Partner contract ID, required if the partner has multiple contracts.
   */
  private Long partnerContractId;
}

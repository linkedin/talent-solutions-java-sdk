package com.linkedin.sdk.lts.jobs.model.request.p4pjobposting;

import com.linkedin.sdk.lts.jobs.model.response.common.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Request schema for fetching LP4P job reports by partner contract ID.
 * This is paginated ApI that allows partners to retrieve job performance metrics.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#check-job-reports-by-date-range">LinkedIn P4P Reports Documentation</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P4PJobReportsRequestByDate {

  /**
   * Date range to fetch reports (inclusive).
   */
  private DateRange dateRange;

  /**
   * Optional: Partner contract ID, required if the partner has multiple contracts.
   */
  private Long partnerContractId;

  /**
   * Pagination parameters for the request.
   */
  private PaginationQueryParams pagination;
}

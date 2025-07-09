package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a response report for a job posting in the Pay for Performance (P4P) system.
 * Contains job posting information and performance metrics.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PReportResponseByDate {

  /**
   * The pagination metadata for the report response.
   */
  private Pagination paging;

  /**
   * The list of performance metrics for the job postings under the specified contract ID.
   */
  private List<P4PJobReport> elements;

}

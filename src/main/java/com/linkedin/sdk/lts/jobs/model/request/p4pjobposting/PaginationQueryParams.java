package com.linkedin.sdk.lts.jobs.model.request.p4pjobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents the pagination parameters for querying LP4P job postings.
 * This class encapsulates the starting index and count of job postings to be retrieved.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#pagination-specific-query-parameters">LinkedIn P4P Reports Documentation</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationQueryParams {

  /**
   * The starting index of the pagination. Defaults to 0 if not provided.
   */
  private Integer start;

  /**
   * The max number of externalJobPostingIds requested. Defaults to 50 if not provided. Maximum allowed is 50.
   */
  private Integer count;
}

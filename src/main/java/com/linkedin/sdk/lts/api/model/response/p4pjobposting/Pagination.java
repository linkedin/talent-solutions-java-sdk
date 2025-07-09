package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents pagination metadata for paged API responses.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class Pagination {

  /**
   * The start index of pagination.
   */
  private Integer start;

  /**
   * The number of items returned.
   */
  private Integer count;

  /**
   * The total number of elements (optional).
   */
  private Integer total;

  /**
   * Context of previous and next page (optional).
   * Previous page does not exist on the first page.
   * Next page does not exist on the last page.
   */
  private List<PaginationLink> links;
}
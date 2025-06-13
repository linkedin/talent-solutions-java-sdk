package com.linkedin.sdk.lts.jobs.model.response.p4pjobposting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a pagination link for navigating to the previous or next page in API responses.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class PaginationLink {

  /**
   * The reference to previous or next page (e.g., "next", "previous").
   */
  private String rel;

  /**
   * The content type of the payload (e.g., "application/json").
   */
  private String type;

  /**
   * The resource URL to the previous or next page.
   */
  private String href;
}
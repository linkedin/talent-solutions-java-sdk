package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an error for a job posting report.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PJobReportError {

  /**
   * The error code.
   */
  private String code;

  /**
   * The error message.
   */
  private String message;

  /**
   * The HTTP status code.
   */
  private Integer status;
}
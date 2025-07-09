package com.linkedin.sdk.lts.api.model.response.p4pjobposting;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a response containing job posting reports and errors for multiple job postings
 * identified by their IDs in the Pay for Performance (P4P) system.
 * Contains a map of job posting reports and a map of errors associated with those reports.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@NoArgsConstructor
public class P4PReportResponseByIds {

  /**
   * P4P job posting reports keyed by job posting IDs.
   */
  private Map<String, P4PJobReport> results;

  /**
   * Errors associated with job postings, keyed by job posting IDs.
   */
  private Map<String, P4PJobReportError> errors;

}

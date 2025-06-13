package com.linkedin.sdk.lts.jobs.model.request.p4pjobposting;

import com.linkedin.sdk.lts.jobs.model.request.jobposting.MoneyAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents the budget information for a job posting in LinkedIn's Job Posting API,
 * particularly in the context of pay-for-performance (P4P) job postings.
 *
 * <p>It contains a single field representing the total budget amount.</p>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema-p4p?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Reports Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class P4PBudget {

  /**
   * The total budget allocated for the job posting.
   */
  private MoneyAmount payForPerformanceTotalBudget;
}

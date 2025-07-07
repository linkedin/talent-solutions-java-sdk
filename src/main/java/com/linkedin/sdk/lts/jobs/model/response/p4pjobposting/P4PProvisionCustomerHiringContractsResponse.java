package com.linkedin.sdk.lts.jobs.model.response.p4pjobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response for provisioning customer hiring contracts in LinkedIn's Job Posting API,
 * particularly in the context of pay-for-performance (P4P) job postings.
 *
 * <p>It contains fields for application identification and contract information.</p>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/provision-customer-contracts?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Provision Customer Contracts</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class P4PProvisionCustomerHiringContractsResponse {

  /**
   * The value object containing the application ID and contract ID.
   * This is a required field that cannot be null.
   */
  private Value value;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Value {

    /**
     * Unique application ID (urn) per client or company. Example: urn:li:developerApplication:012345
     */
    private String key;

    /**
     * Clients can use either this ID or the partnerContractId to manage jobs. Example: urn:li:contract:67890
     */
    private String contract;
  }
}
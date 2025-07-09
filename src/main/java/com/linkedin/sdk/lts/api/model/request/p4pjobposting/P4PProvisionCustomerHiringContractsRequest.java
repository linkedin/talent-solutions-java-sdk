package com.linkedin.sdk.lts.api.model.request.p4pjobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for provisioning customer hiring contracts in LinkedIn's Job Posting API,
 *
 * <p>It contains a single field representing the financial contract ID.</p>
 *
 *  * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/provision-customer-contracts?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04">LinkedIn P4P Provision Customer Contracts</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class P4PProvisionCustomerHiringContractsRequest {

  /**
   * The ID of the financial (parent) contract to which the job product
   * purchased by the partner was provisioned.
   */
  private Long partnerContractId;
}
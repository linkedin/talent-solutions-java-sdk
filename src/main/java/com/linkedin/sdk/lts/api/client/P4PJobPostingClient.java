package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PJobReportsRequestByDate;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PJobReportsRequestByIds;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PProvisionCustomerHiringContractsRequest;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PProvisionCustomerHiringContractsResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByIds;
import lombok.NonNull;



/**
 * Public Interface for interacting with LinkedIn's PayForPerformance Job Posting API.
 * </pre>
 */
public interface P4PJobPostingClient extends JobPostingClient {

  /**
   * Provision customer hiring contracts for Pay for Performance (P4P).
   *
   * @param p4PProvisionCustomerHiringContractsRequest the request containing contract details
   * @return the P4PProvisionCustomerHiringContractsResponse containing the result of the operation
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if any parameters are invalid
   * @throws JsonSerializationException if there is an error serializing the request
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<P4PProvisionCustomerHiringContractsResponse> provisionCustomerHiringContracts(
      @NonNull P4PProvisionCustomerHiringContractsRequest p4PProvisionCustomerHiringContractsRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonSerializationException,
             JsonDeserializationException;


  /**
   * Get the Pay for Performance (P4P) report for a job posting by IDs.
   *
   * @param p4PJobReportsRequestByIds the request containing contract ID and job posting IDs
   * @return the P4PReportResponseByIds containing the performance metrics
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if any parameters are invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<P4PReportResponseByIds> getP4PReportByIds(@NonNull P4PJobReportsRequestByIds p4PJobReportsRequestByIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;

  /**
   * Get the Pay for Performance (P4P) report for a job posting by date.
   *
   * @param p4PJobPostingRequestByDate the request containing contract ID and date range
   * @return the P4PReportResponseByDate containing the performance metrics
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if any parameters are invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<P4PReportResponseByDate> getP4PReportsByDate(@NonNull P4PJobReportsRequestByDate p4PJobPostingRequestByDate)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;

  /**
   * Get the budget reports for a partner contract ID.
   *
   * @param partnerContractId the partner contract ID to fetch reports for
   * @return the P4PBudgetReportResponse containing budget information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the partner contract ID is invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<P4PBudgetReportResponse> getPartnerBudgetReports(@NonNull Long partnerContractId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;
}


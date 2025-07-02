package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.jobs.exception.AuthenticationException;
import com.linkedin.sdk.lts.jobs.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.jobs.exception.JsonSerializationException;
import com.linkedin.sdk.lts.jobs.exception.LinkedInApiException;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByDate;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByIds;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PProvisionCustomerHiringContractsRequest;
import com.linkedin.sdk.lts.jobs.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PProvisionCustomerHiringContractsResponse;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByIds;
import com.linkedin.sdk.lts.jobs.model.response.common.Date;
import com.linkedin.sdk.lts.jobs.model.response.common.DateRange;
import com.linkedin.sdk.lts.jobs.model.response.common.HttpStatusCategory;
import com.linkedin.sdk.lts.jobs.util.ObjectMapperUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.linkedin.sdk.lts.jobs.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.jobs.constants.LinkedInApiConstants.*;
import static com.linkedin.sdk.lts.jobs.constants.LinkedInApiConstants.P4P_REPORTS_API_CONSTANTS.*;


/**
 * A client for interacting with LinkedIn's PayForPerformance Job Posting API.
 * </pre>
 */
public class P4PJobPostingClient extends JobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(P4PJobPostingClient.class.getName());

  /**
   * Constructs a new P4PJobPostingClient with the specified OAuth 2.0 configuration and HTTP client.
   *
   * @param config the OAuth 2.0 configuration for this provider
   * @param httpClient the HTTP client to use for making requests
   */
  protected P4PJobPostingClient(OAuth2Config config, HttpClient httpClient) {
    super(config, httpClient);
  }

  /**
   * Get the Pay for Performance (P4P) report for a job posting by Ids.
   *
   * @return the P4PReportResponseByIds containing the performance metrics
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if any parameters are invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public P4PReportResponseByIds getP4PReportByIds(P4PJobReportsRequestByIds p4PJobReportsRequestByIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if(p4PJobReportsRequestByIds == null) {
        throw new IllegalArgumentException("P4PJobReportsRequestByIds cannot be null");
      }

      List<String> ids = p4PJobReportsRequestByIds.getIds();
      DateRange dateRange = p4PJobReportsRequestByIds.getDateRange();
      Long partnerContractId = p4PJobReportsRequestByIds.getPartnerContractId();

      if(ids == null || ids.isEmpty()) {
        throw new IllegalArgumentException("Ids cannot be null or empty");
      }

      if(dateRange == null || dateRange.getStart() == null || dateRange.getEnd() == null) {
        throw new IllegalArgumentException("Date range cannot be null and must have both start and end dates");
      }

      // Build the URL with query parameters
      String queryParams = IDS + EQUALS_SEPARATOR + LIST + OPENING_BRACKET + String.join(SEMICOLON_SEPARATOR, ids) + CLOSING_BRACKET +
          QUERY_PARAM_SEPARATOR + formatDateRange(dateRange);

      if(partnerContractId != null) {
        queryParams += QUERY_PARAM_SEPARATOR + PARTNER_CONTRACT_ID + EQUALS_SEPARATOR + partnerContractId;
      }
      String url = PARTNER_JOB_REPORTS_BASE_URL + QUERY_SEPARATOR + queryParams;

      String response = this.httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);
      return ObjectMapperUtil.fromJson(response, P4PReportResponseByIds.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }
  }

  /**
   * Get the Pay for Performance (P4P) report for a job posting by date.
   *
   * @param p4PJobPostingRequestByDate the request containing contract ID and date range
   * @return the P4PReportResponseByDate containing the performance metrics
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if any parameters are invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public P4PReportResponseByDate getP4PReportsByDate(
      P4PJobReportsRequestByDate p4PJobPostingRequestByDate)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if(p4PJobPostingRequestByDate == null) {
        throw new IllegalArgumentException("P4PJobReportsRequestByDate cannot be null");
      }

      DateRange dateRange = p4PJobPostingRequestByDate.getDateRange();
      if(dateRange == null || dateRange.getStart() == null || dateRange.getEnd() == null) {
        throw new IllegalArgumentException("Date range cannot be null and must have both start and end dates");
      }

      Long partnerContractId = p4PJobPostingRequestByDate.getPartnerContractId();
      Integer paginationStart = 0;
      Integer paginationCount = 50;
      if(p4PJobPostingRequestByDate.getPagination() != null) {
        paginationStart = p4PJobPostingRequestByDate.getPagination().getStart();
        paginationCount = p4PJobPostingRequestByDate.getPagination().getCount();
      }

      // Build the URL with query parameters
      String queryParams = QUERY + EQUALS_SEPARATOR + DATE_RANGE + QUERY_PARAM_SEPARATOR + formatDateRange(dateRange);
      if(partnerContractId != null){
        queryParams += QUERY_PARAM_SEPARATOR + PARTNER_CONTRACT_ID + EQUALS_SEPARATOR + partnerContractId;
      }
      queryParams += QUERY_PARAM_SEPARATOR + START + EQUALS_SEPARATOR + paginationStart + QUERY_PARAM_SEPARATOR
          + COUNT + EQUALS_SEPARATOR + paginationCount;

      String url = PARTNER_JOB_REPORTS_BASE_URL + QUERY_SEPARATOR + queryParams;

      String response = this.httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);
      return ObjectMapperUtil.fromJson(response, P4PReportResponseByDate.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }
  }

  /**
   * Get the budget reports for a partner contract ID.
   *
   * @param partnerContractId the partner contract ID to fetch reports for
   * @return the P4PBudgetReportResponse containing budget information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the partner contract ID is invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public P4PBudgetReportResponse getPartnerBudgetReports(Long partnerContractId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (partnerContractId == null) {
        throw new IllegalArgumentException("Partner contract ID cannot be null");
      }
      // Build the URL with query parameters
      String queryParams = PARTNER_CONTRACT_ID + EQUALS_SEPARATOR + partnerContractId;
      String url = PARTNER_BUDGET_REPORTS_BASE_URL + QUERY_SEPARATOR + queryParams;

      String response = this.httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);
      return ObjectMapperUtil.fromJson(response, P4PBudgetReportResponse.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }

  }

  /**
   * Constructs the headers required for the P4P API requests.
   *
   * @return a map of headers to be used in the request
   * @throws AuthenticationException if authentication fails
   */
  private Map<String, String> getHeadersForAPI() throws AuthenticationException {
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    headers.put(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
    headers.put(LINKEDIN_VERSION, API_VERSION_2025_04);
    headers.put(X_REST_LI_PROTOCOL_VERSION, X_REST_LI_PROTOCOL_VERSION_VALUE_2_0_0);
    return headers;
  }

  /**
   * Formats a DateRange object into a string representation.
   *
   * @param dateRange the DateRange object to format
   * @return a string representation of the date range
   */
  private static String formatDateRange(DateRange dateRange) throws IOException {
    return DATE_RANGE + EQUALS_SEPARATOR + OPENING_BRACKET
        + START + COLON_SEPARATOR + formatDate(dateRange.getStart()) + SEMICOLON_SEPARATOR
        + END + COLON_SEPARATOR + formatDate(dateRange.getEnd())
        + CLOSING_BRACKET;
  }

  /**
   * Formats a Date object into a string representation.
   *
   * @param date the Date object to format
   * @return a string representation of the date
   */
  private static String formatDate(Date date) {
    return OPENING_BRACKET
        + YEAR + COLON_SEPARATOR + date.getYear() + COMMA_SEPARATOR
        + MONTH + COLON_SEPARATOR + date.getMonth() + COMMA_SEPARATOR
        + DAY + COLON_SEPARATOR + date.getDay()
        + CLOSING_BRACKET;
  }
}


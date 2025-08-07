package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.auth.OAuth2Provider;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.jobposting.JobPostingRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;
import com.linkedin.sdk.lts.api.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.api.model.response.jobtaskstatus.JobTaskStatusResponse;
import com.linkedin.sdk.lts.internal.util.LogRedactor;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import com.linkedin.sdk.lts.api.client.JobPostingClient;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.extern.java.Log;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.*;


/**
 * A client implementation for interacting with LinkedIn's Basic Job Posting API.
 * This class implements {@link JobPostingClient} and provides functionality to create,
 * update, renew, and close job postings through LinkedIn's Job Posting API endpoints.
 *
 * </pre>
 */
public class JobPostingClientImpl implements JobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(JobPostingClientImpl.class.getName());

  /**
   * The OAuth 2.0 configuration for this client instance.
   */
  protected final OAuth2Config oAuth2Config;

  /**
   * The HTTP client used to make requests to LinkedIn's API.
   */
  protected final HttpClient httpClient;

  /**
   * Constructs a new JobPostingClient with the specified OAuth 2.0 configuration and HTTP client.
   *
   * @param config the OAuth 2.0 configuration for this provider
   * @param httpClient the HTTP client to use for making requests
   */
  public JobPostingClientImpl(OAuth2Config config, HttpClient httpClient) {
    this.oAuth2Config = config;
    this.httpClient = httpClient;
  }

  /**
   * JobPosting Request of type Create, Update, Close, Renew
   *
   * @param jobPostingRequest the BasicJobPostingRequest containing the job posting data
   * @return the API response as a JSON string
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the request fails validation
   * @throws LinkedInApiException if the API returns an error response
   */
  public JobPostingResponse processJobPosting(JobPostingRequest jobPostingRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (jobPostingRequest == null) {
        throw new IllegalArgumentException("Job Posting Request cannot be null");
      }
      String requestBody = ObjectMapperUtil.toJson(jobPostingRequest);

      Map<String, String> headers = getHeadersForAPI();
      headers.put(X_REST_LI_METHOD, BATCH_CREATE);

      String response = httpClient.executeRequest(JOB_POSTING_BASE_URL, HttpMethod.POST, headers, requestBody);

      return ObjectMapperUtil.fromJson(response, JobPostingResponse.class);
    } catch (JsonSerializationException e) {
      String errorMessage = "Failed to serialize request: " + e.getMessage();
      LOGGER.severe(LogRedactor.redact(errorMessage));
      throw new LinkedInApiException(HttpStatusCategory.CLIENT_ERROR.getDefaultCode(), "Invalid request format",
          errorMessage);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(LogRedactor.redact(errorMessage));
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, LogRedactor.redact(errorMessage), e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }
  }

  /**
   * Get the status of a single job posting task.
   *
   * @param taskId the ID of the job posting task to check
   * @return the JobTaskStatusResponse containing the task status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the taskId is invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public JobTaskStatusResponse getTaskStatus(String taskId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    if (taskId == null || taskId.isEmpty()) {
      LOGGER.severe("Task ID cannot be null or empty");
      throw new IllegalArgumentException("Task ID cannot be null or empty");
    }

    return getTaskStatus(Arrays.asList(taskId));
  }

  /**
   * Get the status of multiple job posting tasks.
   *
   * @param taskIds a list of task IDs to check
   * @return the JobTaskStatusResponse containing the tasks status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the taskIds list is invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public JobTaskStatusResponse getTaskStatus(List<String> taskIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (taskIds == null || taskIds.isEmpty()) {
        LOGGER.severe("Task IDs list cannot be null or empty");
        throw new IllegalArgumentException("Task IDs list cannot be null or empty");
      }

      // Format the task IDs for the query parameter
      String taskIdsParam = taskIds.stream().map(id -> "ids=" + id).collect(Collectors.joining(QUERY_PARAM_SEPARATOR));
      String url = JOB_TASK_STATUS_BASE_URL + QUERY_SEPARATOR + taskIdsParam;

      String response = httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);
      return ObjectMapperUtil.fromJson(response, JobTaskStatusResponse.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(LogRedactor.redact(errorMessage));
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, LogRedactor.redact(errorMessage), e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }
  }

  /**
   * Get the status of a single job posting.
   *
   * @param jobPostingId the ID of the job posting to check
   * @return the JobPostingStatusResponse containing the job posting status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the jobPostingId is invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public JobPostingStatusResponse getJobPostingStatus(String jobPostingId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException{
    if (jobPostingId == null || jobPostingId.isEmpty()) {
      LOGGER.severe("Job Posting ID cannot be null or empty");
      throw new IllegalArgumentException("Job Posting ID cannot be null or empty");
    }
    return getJobPostingStatus(Arrays.asList(jobPostingId));
  }

  /**
   * Get the status of multiple job postings.
   *
   * @param jobPostingIds a list of external job posting IDs to check
   * @return the JobPostingStatusResponse containing the job posting status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the jobPostingIds list is invalid
   * @throws LinkedInApiException if the API returns an error response
   */
  public JobPostingStatusResponse getJobPostingStatus(List<String> jobPostingIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (jobPostingIds == null || jobPostingIds.isEmpty()) {
      LOGGER.severe("Job Posting IDs list cannot be null or empty");
        throw new IllegalArgumentException("Job Posting IDs list cannot be null or empty");
      }

      String jobPostingIdsParams =
          jobPostingIds.stream().map(id -> "ids=" + id).collect(Collectors.joining(QUERY_PARAM_SEPARATOR));
      String url = JOB_STATUS_BASE_URL + QUERY_SEPARATOR + jobPostingIdsParams;

      String response = httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);
      return ObjectMapperUtil.fromJson(response, JobPostingStatusResponse.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(LogRedactor.redact(errorMessage));
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error", errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, LogRedactor.redact(errorMessage), e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error", errorMessage);
    }
  }

  /**
   * Constructs the headers required for the JobPosting API requests.
   *
   * @return a map of headers to be used in the request
   * @throws AuthenticationException if authentication fails
   */
  private Map<String, String> getHeadersForAPI() throws AuthenticationException {
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    headers.put(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
    headers.put(LINKEDIN_VERSION, API_VERSION_2025_04);
    headers.put(X_EXTERNAL_USER, oAuth2Config.getClientId());
    return headers;
  }

  /**
   * Method to get the access token from the OAuth2Provider.
   * Separated for testability
   *
   * @return the access token
   * @throws AuthenticationException if authentication fails
   */
  protected String getAccessToken() throws AuthenticationException {
    return OAuth2Provider.getInstance(oAuth2Config, httpClient).getAccessToken();
  }
}

package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.auth.OAuth2Provider;
import com.linkedin.sdk.lts.jobs.exception.AuthenticationException;
import com.linkedin.sdk.lts.jobs.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.jobs.exception.JsonSerializationException;
import com.linkedin.sdk.lts.jobs.exception.LinkedInApiException;
import com.linkedin.sdk.lts.jobs.model.request.jobposting.JobPostingRequest;
import com.linkedin.sdk.lts.jobs.model.response.common.HttpStatusCategory;
import com.linkedin.sdk.lts.jobs.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus.JobTaskStatusResponse;
import com.linkedin.sdk.lts.jobs.util.ObjectMapperUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.OutputStream;

import static com.linkedin.sdk.lts.jobs.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.jobs.constants.LinkedInApiConstants.*;


/**
 * A client for interacting with LinkedIn's Basic Job Posting API.
 * This client provides functionality to create, update, renew, and close job postings
 * through LinkedIn's v2 Job Posting API endpoints.
 *
 * <p>This class implements the Singleton pattern per configuration, ensuring only one instance
 * exists per unique {@link OAuth2Config}. Instances should be obtained using the
 * {@link #getInstance(OAuth2Config)} factory method.</p>
 *
 * <p>Thread Safety: This class is thread-safe. It maintains no mutable state and uses
 * {@link ConcurrentHashMap} for instance management. Each operation creates its own
 * connection and performs request validation independently.</p>
 *
 * </pre>
 */
public class JobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(JobPostingClient.class.getName());

  /**
   * Cache of client instances keyed by their OAuth configurations.
   * Uses ConcurrentHashMap to ensure thread-safe access to client instances.
   */
  private static final ConcurrentHashMap<OAuth2Config, JobPostingClient> INSTANCES = new ConcurrentHashMap<>();

  /**
   * The OAuth 2.0 configuration for this client instance.
   */
  private final OAuth2Config oAuth2Config;

  /**
   * Private constructor enforcing singleton pattern per configuration.
   *
   * @param config the OAuth 2.0 configuration for this provider
   */
  private JobPostingClient(OAuth2Config config) {
    this.oAuth2Config = config;
  }

  /**
   * Factory method to obtain a BasicJobPostingClient instance for a given configuration.
   * If an instance already exists for the provided configuration, it will be returned.
   * Otherwise, a new instance will be created.
   *
   * @param config the OAuth 2.0 configuration to use
   * @return JobPostingClient instance for the given configuration
   * @throws NullPointerException if config is null
   */
  public static synchronized JobPostingClient getInstance(OAuth2Config config) {
    return INSTANCES.computeIfAbsent(config, JobPostingClient::new);
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
  public JobPostingResponse post(JobPostingRequest jobPostingRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (jobPostingRequest == null) {
        throw new IllegalArgumentException("Job Posting Request cannot be null");
      }
      String requestBody = ObjectMapperUtil.toJson(jobPostingRequest);

      URL url = new URL(JOB_POSTING_BASE_URL);
      HttpsURLConnection connection = openConnection(url);

      connection.setRequestMethod(POST);
      connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
      connection.setRequestProperty(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
      connection.setRequestProperty(X_REST_LI_METHOD, BATCH_CREATE);
      connection.setDoOutput(true);

      OutputStream os = connection.getOutputStream();
      os.write(requestBody.getBytes());
      os.flush();

      String response = getResponseBody(connection);
      return ObjectMapperUtil.fromJson(response, JobPostingResponse.class);
    } catch (JsonSerializationException e) {
      String errorMessage = "Failed to serialize request: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.CLIENT_ERROR.getDefaultCode(), "Invalid request format",
          errorMessage);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.severe(errorMessage);
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

      // Build the URL with query parameters
      URL url = new URL(JOB_TASK_STATUS_BASE_URL + QUERY_SEPARATOR + taskIdsParam);
      HttpsURLConnection connection = openConnection(url);

      // Set up the connection
      connection.setRequestMethod(GET);
      connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
      connection.setRequestProperty(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
      connection.setDoOutput(false);

      String response = getResponseBody(connection);
      return ObjectMapperUtil.fromJson(response, JobTaskStatusResponse.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
    LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.severe(errorMessage);
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

      // Format the job IDs for the query parameter
      String jobPostingIdsParams =
          jobPostingIds.stream().map(id -> "ids=" + id).collect(Collectors.joining(QUERY_PARAM_SEPARATOR));

      // Build the URL with query parameters
      URL url = new URL(JOB_STATUS_BASE_URL + QUERY_SEPARATOR + jobPostingIdsParams);
      HttpsURLConnection connection = openConnection(url);

      // Set up the connection
      connection.setRequestMethod(GET);
      connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
      connection.setRequestProperty(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
      connection.setDoOutput(false);

      String response = getResponseBody(connection);
      return ObjectMapperUtil.fromJson(response, JobPostingStatusResponse.class);
    } catch (JsonDeserializationException e) {
      String errorMessage = "Failed to parse LinkedIn API response: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Response parsing error", errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error", errorMessage);
    }
  }

  /**
   * Opens a new HttpsURLConnection to the specified URL.
   * Separated for testability.
   *
   * @param url the URL to open a connection to
   * @return the opened HttpsURLConnection
   * @throws IOException if an I/O error occurs while opening the connection
   */
  protected HttpsURLConnection openConnection(URL url) throws IOException {
    return (HttpsURLConnection) url.openConnection();
  }

  /**
   * Method to get the access token from the OAuth2Provider.
   * Separated for testability
   *
   * @return the access token
   * @throws AuthenticationException if authentication fails
   */
  protected String getAccessToken() throws AuthenticationException {
    return OAuth2Provider.getInstance(oAuth2Config).getAccessToken();
  }

  /**
   * Reads the response body from the connection.
   * @param connection the HttpsURLConnection to read from
   * @return the response body as a string
   * @throws LinkedInApiException if the response code indicates an error
   * @throws IOException if an I/O error occurs while reading the response
   */
  private String getResponseBody(HttpsURLConnection connection) throws LinkedInApiException, IOException {
    int responseCode = connection.getResponseCode();
    InputStream inputStream = isSuccessResponse(responseCode)
        ? connection.getInputStream()
        : connection.getErrorStream();

    String response = readStream(inputStream);
    LOGGER.info("Response body: " + response);

    if (!HttpStatusCategory.SUCCESS.matches(responseCode)) {
      String errorMessage = "HTTP error " + responseCode + ": " + response;
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(responseCode, response, errorMessage);
    }
    return response;
  }

  /**
   * Reads the input stream and returns its content as a string.
   *
   * @param inputStream the input stream to read
   * @return the content of the input stream as a string
   * @throws IOException if an I/O error occurs
   */
  private String readStream(InputStream inputStream) throws IOException {
    if (inputStream == null) return "";
    StringBuilder response = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
    }catch (IOException e) {
      LOGGER.severe("Error reading from stream: " + e.getMessage());
      throw e;
    }
    return response.toString();
  }

  /**
   * Method to check if the response code indicates a successful response.
   * @param httpResponseCode
   * @return
   */
  private boolean isSuccessResponse(int httpResponseCode) {
    return HttpStatusCategory.SUCCESS.matches(httpResponseCode);
  }

}

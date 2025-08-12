package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.ApplyConnectJobPostingClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.applyconnect.jobApplicationNotification.JobApplicationNotificationRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.internal.util.LogRedactor;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.*;


/**
 * Implementation of the ApplyConnect Job Posting client that handles interactions with LinkedIn's ApplyConnect Job Posting API.
 * This class extends {@link JobPostingClientImpl} for base job posting functionality and implements {@link ApplyConnectJobPostingClient}
 * for ApplyConnect-specific features.
 *
 */
public class ApplyConnectJobPostingClientImpl extends JobPostingClientImpl implements ApplyConnectJobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(ApplyConnectJobPostingClientImpl.class.getName());

  /**
   * Constructs a new ApplyConnectJobPostingClient with the specified OAuth 2.0 configuration and HTTP client.
   *
   * @param config the OAuth 2.0 configuration for this provider
   * @param httpClient the HTTP client to use for making requests
   */
  public ApplyConnectJobPostingClientImpl(OAuth2Config config, HttpClient httpClient) {
    super(config, httpClient);
  }

  /**
   * Synchronizes job application notifications with LinkedIn's ApplyConnect API.
   *
   * @param jobApplicationNotificationRequest the request containing job application notification data
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if the API returns an error response
   * @throws IllegalArgumentException if the request fails validation
   */
  @Override
  public void syncJobApplicationNotification(@NonNull JobApplicationNotificationRequest jobApplicationNotificationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException {
    try {
      if (jobApplicationNotificationRequest == null) {
        throw new IllegalArgumentException("Job Posting Notification Request cannot be null");
      }
      String requestBody = ObjectMapperUtil.toJson(jobApplicationNotificationRequest);

      Map<String, String> headers = new HashMap<>();
      headers.put(X_REST_LI_METHOD, CREATE);
      headers.put(AUTHORIZATION, BEARER + SPACE_SEPARATOR + getAccessToken());
      headers.put(X_EXTERNAL_USER, oAuth2Config.getParentClientId());
      httpClient.executeRequest(SYNC_JOB_APPLICATION_NOTIFICATIONS_URL, HttpMethod.POST, headers, requestBody);
    } catch (JsonSerializationException e) {
      String errorMessage = "Failed to serialize request: " + e.getMessage();
      LOGGER.severe(LogRedactor.redact(errorMessage));
      throw new LinkedInApiException(HttpStatusCategory.CLIENT_ERROR.getDefaultCode(), "Invalid request format",
          errorMessage);
    } catch (IOException e) {
      String errorMessage = "Network error while communicating with LinkedIn API: " + e.getMessage();
      LOGGER.log(Level.SEVERE, LogRedactor.redact(errorMessage), e);
      throw new LinkedInApiException(HttpStatusCategory.SERVER_ERROR.getDefaultCode(), "Network error",
          errorMessage);
    }
  }
}

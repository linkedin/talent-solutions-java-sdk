package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.applyconnect.jobApplicationNotification.JobApplicationNotificationRequest;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import lombok.NonNull;


/**
 * Public Interface for interacting with LinkedIn's ApplyConnect Job Posting API.
 *
 * </pre>
 */
public interface ApplyConnectJobPostingClient extends JobPostingClient {

  /**
   * Synchronizes job application notifications with LinkedIn's ApplyConnect API.
   *
   * @param jobApplicationNotificationRequest the request containing job application notification data
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if the API returns an error response
   * @throws IllegalArgumentException if the request fails validation
   * @throws JsonSerializationException if there is an error serializing the request
   * @throws JsonDeserializationException if there is an error deserializing the response
   */
  APIResponse<Void> syncJobApplicationNotification(@NonNull JobApplicationNotificationRequest jobApplicationNotificationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonSerializationException,
             JsonDeserializationException;
}

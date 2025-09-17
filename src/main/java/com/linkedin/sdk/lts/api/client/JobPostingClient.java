package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.jobposting.JobPostingRequest;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.api.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.api.model.response.jobtaskstatus.JobTaskStatusResponse;
import java.util.List;


/**
 * Public Interface for interacting with LinkedIn's Basic Job Posting API.
 * This client provides functionality to create, update, renew, and close job postings
 * through LinkedIn's v2 Job Posting API endpoints.
 *
 * </pre>
 */
public interface JobPostingClient {

  /**
   * JobPosting Request of type Create, Update, Close, Renew
   *
   * @param jobPostingRequest the BasicJobPostingRequest containing the job posting data
   * @return the API response as a JSON string
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the request fails validation
   * @throws JsonSerializationException if there is an error serializing the request
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<JobPostingResponse> processJobPosting(JobPostingRequest jobPostingRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonSerializationException,
             JsonDeserializationException;

  /**
   * Get the status of a single job posting task.
   *
   * @param taskId the ID of the job posting task to check
   * @return the JobTaskStatusResponse containing the task status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the taskId is invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<JobTaskStatusResponse> getTaskStatus(String taskId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;

  /**
   * Get the status of multiple job posting tasks.
   *
   * @param taskIds a list of task IDs to check
   * @return the JobTaskStatusResponse containing the tasks status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the taskIds list is invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<JobTaskStatusResponse> getTaskStatus(List<String> taskIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;

  /**
   * Get the status of a single job posting.
   *
   * @param jobPostingId the ID of the job posting to check
   * @return the JobPostingStatusResponse containing the job posting status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the jobPostingId is invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<JobPostingStatusResponse> getJobPostingStatus(String jobPostingId)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;

  /**
   * Get the status of multiple job postings.
   *
   * @param jobPostingIds a list of external job posting IDs to check
   * @return the JobPostingStatusResponse containing the job posting status information
   * @throws AuthenticationException if authentication fails
   * @throws IllegalArgumentException if the jobPostingIds list is invalid
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws LinkedInApiException if the API returns an error response
   */
  APIResponse<JobPostingStatusResponse> getJobPostingStatus(List<String> jobPostingIds)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;
}

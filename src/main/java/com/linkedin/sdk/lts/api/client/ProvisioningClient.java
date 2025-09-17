package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.provisioning.CreateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.GetApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequest;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;


/**
 * LinkedIn's Provisioning APIs allow partners to create and configure
 * developer applications on behalf of their customers which is an initial
 * step in managing and enabling their integrations.
 */
public interface ProvisioningClient {

  /**
   * Creates a new child developer application using the provided request.
   * @param createApplicationRequest the request containing application details
   *
   * @return the response containing the created application's details
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if an error occurs while communicating with the LinkedIn API
   * @throws IllegalArgumentException if the request is null or contains invalid parameters
   * @throws JsonSerializationException if there is an error serializing the request
   * @throws JsonDeserializationException if there is an error deserializing the response
   *
   */
   APIResponse<CreateApplicationResponse> createApplication(CreateApplicationRequest createApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonSerializationException,
             JsonDeserializationException;


  /**
   * Updates an existing child developer application using the provided request.
   *
   * @return the response indicating the success of the update operation
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if an error occurs while communicating with the LinkedIn API
   * @throws IllegalArgumentException if the request is null or contains invalid parameters
   * @throws JsonSerializationException if there is an error serializing the request
   * @throws JsonDeserializationException if there is an error deserializing the response
   *
   * @param updateApplicationRequest the request containing updated application details
   */
  APIResponse<Void> updateApplication(UpdateApplicationRequest updateApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonSerializationException, JsonDeserializationException;

  /**
   * Retrieves a child developer application by its unique foreign ID.
   *
   * @param getApplicationRequest the request containing the unique foreign ID
   * @return the response containing application details
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if an error occurs while communicating with the LinkedIn API
   * @throws JsonDeserializationException if there is an error deserializing the response
   * @throws IllegalArgumentException if the request is null or contains invalid parameters
   */
  APIResponse<GetApplicationResponse> getApplication(GetApplicationRequest getApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException, JsonDeserializationException;
}

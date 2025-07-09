package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.provisioning.CreateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.GetApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequest;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;


/**
 * Public Interface LinkedIn's Provisioning APIs allow Partners to create and configure
 * developer applications on behalf of their Customers which is an initial
 * step in managing and enabling their integrations.
 */
public interface ProvisioningClient {

  /**
   * Creates a new child developer application using the provided request.
   *
   * @param createApplicationRequest the request containing application details
   */
   CreateApplicationResponse createApplication(CreateApplicationRequest createApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;


  /**
   * Updates an existing child developer application using the provided request.
   *
   * @param updateApplicationRequest the request containing updated application details
   */
  void updateApplication(UpdateApplicationRequest updateApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;

  /**
   * Retrieves a child developer application by its unique foreign ID.
   *
   * @param getApplicationRequest the request containing the unique foreign ID
   * @return the response containing application details
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if an error occurs while communicating with the LinkedIn API
   * @throws IllegalArgumentException if the request is null or contains invalid parameters
   */
  GetApplicationResponse getApplication(GetApplicationRequest getApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;
}

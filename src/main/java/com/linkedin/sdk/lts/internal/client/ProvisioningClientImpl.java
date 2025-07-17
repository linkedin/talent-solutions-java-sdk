package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.ProvisioningClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.provisioning.CreateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.GetApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequestInternal;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.auth.OAuth2Provider;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.internal.util.LogRedactor;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.*;
import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.PROVISIONING_API_CONSTANTS.*;


/**
 * Implementation of LinkedIn's Provisioning API client that enables partners to manage developer applications
 * for their customers. This class implements {@link ProvisioningClient} interface and provides functionality
 * to create, update, and retrieve developer applications through LinkedIn's Provisioning APIs.
 *
 */
public class ProvisioningClientImpl implements ProvisioningClient {

  private static final Logger LOGGER = Logger.getLogger(ProvisioningClientImpl.class.getName());

  protected final OAuth2Config oAuth2Config;

  protected final HttpClient httpClient;

  /**
   * Constructor for ProvisioningClient.
   *
   * @param config the OAuth 2.0 configuration for this provider
   * @param httpClient the HTTP client to use for making requests
   */
  public ProvisioningClientImpl(OAuth2Config config, HttpClient httpClient) {
    this.oAuth2Config = config;
    this.httpClient = httpClient;
  }

  /**
   * Creates a new child developer application using the provided request.
   *
   * @param createApplicationRequest the request containing application details
   */
  public CreateApplicationResponse createApplication(CreateApplicationRequest createApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException{
    try {
      if(createApplicationRequest == null) {
        throw new IllegalArgumentException("CreateApplicationRequest cannot be null");
      }

      String requestBody = ObjectMapperUtil.toJson(createApplicationRequest);
      String response = this.httpClient.executeRequest(PROVISIONING_APPLICATION_BASE_URL,
        HttpMethod.POST, getHeadersForAPI() , requestBody);

      return ObjectMapperUtil.fromJson(response, CreateApplicationResponse.class);
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
   * Updates an existing child developer application using the provided request.
   *
   * @param updateApplicationRequest the request containing updated application details
   */
  public void updateApplication(UpdateApplicationRequest updateApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException{
    try {
      if(updateApplicationRequest == null) {
        throw new IllegalArgumentException("CreateApplicationRequest cannot be null");
      }

      if(updateApplicationRequest.getDeveloperApplicationUrn() == null || updateApplicationRequest.getDeveloperApplicationUrn().isEmpty()) {
        throw new IllegalArgumentException("DeveloperApplicationUrn cannot be null or empty");
      }

      String url = PROVISIONING_APPLICATION_BASE_URL + updateApplicationRequest.getDeveloperApplicationUrn();
      String requestBody = ObjectMapperUtil.toJson(UpdateApplicationRequestInternal.fromUpdateRequest(updateApplicationRequest));
      this.httpClient.executeRequest(url,
          HttpMethod.POST, getHeadersForAPI() , requestBody);
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

  /**
   * Retrieves a child developer application by its unique foreign ID.
   *
   * @param getApplicationRequest the request containing the unique foreign ID
   * @return the response containing application details
   * @throws AuthenticationException if authentication fails
   * @throws LinkedInApiException if an error occurs while communicating with the LinkedIn API
   * @throws IllegalArgumentException if the request is null or contains invalid parameters
   */
  public GetApplicationResponse getApplication(GetApplicationRequest getApplicationRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException{
    try {
      if(getApplicationRequest == null) {
        throw new IllegalArgumentException("GetApplicationRequest cannot be null");
      }

      if(getApplicationRequest.getUniqueForeignId() == null || getApplicationRequest.getUniqueForeignId().isEmpty()) {
        throw new IllegalArgumentException("UniqueForeignId cannot be null or empty");
      }

      String url = PROVISIONING_APPLICATION_BASE_URL + QUERY + EQUALS_SEPARATOR + CREDENTIALS_BY_UNIQUE_FOREIGN_ID + QUERY_PARAM_SEPARATOR +
          UNIQUE_FOREIGN_ID + EQUALS_SEPARATOR + getApplicationRequest.getUniqueForeignId();
      String response = this.httpClient.executeRequest(url, HttpMethod.GET, getHeadersForAPI(), null);

      return ObjectMapperUtil.fromJson(response, GetApplicationResponse.class);
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

package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.P4PJobPostingClientImpl;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.internal.client.linkedinclient.LinkedInHttpClient;
import com.linkedin.sdk.lts.internal.client.ApplyConnectJobPostingClientImpl;
import com.linkedin.sdk.lts.internal.client.JobPostingClientImpl;
import com.linkedin.sdk.lts.internal.client.ProvisioningClientImpl;

import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.*;

/**
 * Public Factory for creating and managing LinkedIn API client instances.
 * Ensures only one client of each type exists per unique clientId/clientSecret pair.
 */
public class LinkedInClientFactory {

  private static final LinkedInClientFactory INSTANCE = new LinkedInClientFactory();
  private final HttpClient httpClient;


  /**
   * Private constructor to prevent instantiation.
   * Use getInstance() to obtain the singleton instance.
   */
  private LinkedInClientFactory() {
    httpClient = new LinkedInHttpClient();
  }

  /**
   * Gets the singleton instance of LinkedInClientFactory.
   *
   * @return the singleton instance
   */
  public static LinkedInClientFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Creates a JobPostingClient for the given credentials.
   *
   * @param clientId the OAuth 2.0 client ID
   * @param clientSecret the OAuth 2.0 client secret
   * @return JobPostingClient instance for the given credentials
   * @throws IllegalArgumentException if clientId or clientSecret is null or empty
   */
  @SuppressWarnings("unchecked")
  public synchronized JobPostingClient getJobPostingClient(String clientId, String clientSecret) {
    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or empty");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client Secret cannot be null or empty");
    }

    OAuth2Config config = OAuth2Config.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .tokenUrl(LINKEDIN_ACCESS_TOKEN_URL)
        .build();

    return new JobPostingClientImpl(config, httpClient);
  }

  /**
   * Creates a P4PJobPostingClient for the given credentials.
   *
   * @param clientId the OAuth 2.0 client ID
   * @param clientSecret the OAuth 2.0 client secret
   * @return JobPostingClient instance for the given credentials
   * @throws IllegalArgumentException if clientId or clientSecret is null or empty
   */
  @SuppressWarnings("unchecked")
  public synchronized P4PJobPostingClient getP4PJobPostingClient(String clientId, String clientSecret) {
    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or empty");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client Secret cannot be null or empty");
    }

    OAuth2Config config = OAuth2Config.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .tokenUrl(LINKEDIN_ACCESS_TOKEN_URL)
        .build();

    return new P4PJobPostingClientImpl(config, httpClient);
  }

  /**
   * Creates a ApplyConnectJobPostingClient for the given credentials.
   *
   * @param clientId the OAuth 2.0 client ID
   * @param clientSecret the OAuth 2.0 client secret
   * @return JobPostingClient instance for the given credentials
   * @throws IllegalArgumentException if clientId or clientSecret is null or empty
   */
  @SuppressWarnings("unchecked")
  public synchronized ApplyConnectJobPostingClient getApplyConnectJobPostingClient(String clientId, String clientSecret) {
    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or empty");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client Secret cannot be null or empty");
    }

    OAuth2Config config = OAuth2Config.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .tokenUrl(LINKEDIN_ACCESS_TOKEN_URL)
        .build();

    return new ApplyConnectJobPostingClientImpl(config, httpClient);
  }

  /**
   * Creates a provisioning clients for the given credentials.
   * Provisioning clients are used to manage LinkedIn applications and their configurations.
   * Pass parent application credentials to this method to create a client that can manage applications.
   *
   * @param clientId the OAuth 2.0 client ID
   * @param clientSecret the OAuth 2.0 client secret
   * @return JobPostingClient instance for the given credentials
   * @throws IllegalArgumentException if clientId or clientSecret is null or empty
   */
  @SuppressWarnings("unchecked")
  public synchronized ProvisioningClient getProvisioningClient(String clientId, String clientSecret) {
    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or empty");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client Secret cannot be null or empty");
    }

    OAuth2Config config = OAuth2Config.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .tokenUrl(LINKEDIN_ACCESS_TOKEN_URL)
        .build();

    return new ProvisioningClientImpl(config, httpClient);
  }
}
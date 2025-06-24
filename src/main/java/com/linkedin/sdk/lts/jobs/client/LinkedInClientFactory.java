package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;

import static com.linkedin.sdk.lts.jobs.constants.LinkedInApiConstants.*;


/**
 * Factory for creating and managing LinkedIn API client instances.
 * Ensures only one client of each type exists per unique clientId/clientSecret pair.
 */
public class LinkedInClientFactory {

  private static final LinkedInClientFactory INSTANCE = new LinkedInClientFactory();

  /**
   * Private constructor to prevent instantiation.
   * Use getInstance() to obtain the singleton instance.
   */
  private LinkedInClientFactory() {
    // Prevent instantiation
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
   * Gets or creates a JobPostingClient for the given credentials.
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

    return JobPostingClient.getInstance(config);
  }

  /**
   * Gets or creates a P4PJobPostingClient for the given credentials.
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

    return P4PJobPostingClient.getInstance(config);
  }

  /**
   * Gets or creates a ApplyConnectJobPostingClient for the given credentials.
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

    return ApplyConnectJobPostingClient.getInstance(config);
  }

  /**
   * Clears all cached client instances.
   */
  protected synchronized void clearInstances() {
    JobPostingClient.clearInstances();
    P4PJobPostingClient.clearInstances();
    ApplyConnectJobPostingClient.clearInstances();
  }

}
package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.ApplyConnectJobPostingClient;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import java.util.logging.Logger;


/**
 * Implementation of the ApplyConnect Job Posting client that handles interactions with LinkedIn's ApplyConnect Job Posting API.
 * This class extends {@link JobPostingClientImpl} for base job posting functionality and implements {@link ApplyConnectJobPostingClient}
 * for ApplyConnect-specific features.
 *
 * We will add more methods to this class as per the requirements of the ApplyConnect APIs.
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
}

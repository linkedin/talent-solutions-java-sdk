package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.client.linkedinclient.HttpClient;
import java.util.logging.Logger;


/**
 * A client for interacting with LinkedIn's ApplyConnect Job Posting API.
 *
 * </pre>
 */
public class ApplyConnectJobPostingClient extends JobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(ApplyConnectJobPostingClient.class.getName());

  /**
   * Constructs a new ApplyConnectJobPostingClient with the specified OAuth 2.0 configuration and HTTP client.
   *
   * @param config the OAuth 2.0 configuration for this provider
   * @param httpClient the HTTP client to use for making requests
   */
  protected ApplyConnectJobPostingClient(OAuth2Config config, HttpClient httpClient) {
    super(config, httpClient);
  }
}

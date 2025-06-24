package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


/**
 * A client for interacting with LinkedIn's ApplyConnect Job Posting API.
 *
 *
 * <p>This class implements the Singleton pattern per configuration, ensuring only one instance
 * exists per unique {@link OAuth2Config}. Instances should be obtained using the
 * {@link #getInstance(OAuth2Config)} factory method.</p>
 *
 * <p>Thread Safety: This class is thread-safe. It maintains no mutable state and uses
 * {@link ConcurrentHashMap} for instance management. Each operation creates its own
 * connection and performs request validation independently.</p>
 *
 * </pre>
 */
public class ApplyConnectJobPostingClient extends JobPostingClient {

  private static final Logger LOGGER = Logger.getLogger(ApplyConnectJobPostingClient.class.getName());

  /**
   * Cache of client instances keyed by their OAuth configurations.
   * Uses ConcurrentHashMap to ensure thread-safe access to client instances.
   */
  private static final ConcurrentHashMap<OAuth2Config, ApplyConnectJobPostingClient> INSTANCES = new ConcurrentHashMap<>();


  /**
   * Private constructor enforcing singleton pattern per configuration.
   *
   * @param config the OAuth 2.0 configuration for this provider
   */
  private ApplyConnectJobPostingClient(OAuth2Config config) {
    super(config);
  }

  /**
   * Factory method to obtain a ApplyConnectJobPostingClient instance for a given configuration.
   * If an instance already exists for the provided configuration, it will be returned.
   * Otherwise, a new instance will be created.
   * Use LinkedInClientFactory to get ApplyConnectJobPostingClient Instance
   * @param config the OAuth 2.0 configuration to use
   * @return ApplyConnectJobPostingClient instance for the given configuration
   * @throws NullPointerException if config is null
   */
  protected static synchronized ApplyConnectJobPostingClient getInstance(OAuth2Config config) {
    return INSTANCES.computeIfAbsent(config, ApplyConnectJobPostingClient::new);
  }

  /**
   * Method to clear all cached instances.
   */
  protected static synchronized void clearInstances() {
    INSTANCES.clear();
  }
}

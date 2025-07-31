package com.linkedin.sdk.lts.internal.auth;

import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;


/**
 * Implementation of {@link AuthenticationProvider} that handles OAuth 2.0 client credentials flow
 * authentication for LinkedIn APIs. This class manages OAuth 2.0 access tokens, including
 * obtaining new tokens and refreshing expired ones.
 *
 * <p>This class implements the Singleton pattern per configuration, ensuring only one instance
 * exists per unique {@link OAuth2Config}. Instances should be obtained using the
 * {@link #getInstance(OAuth2Config, HttpClient)} factory method.</p>
 *
 * <p>Thread Safety: This class is thread-safe. It uses synchronized methods and volatile variables
 * to ensure proper concurrent access to shared resources. The token cache is managed using
 * {@link ConcurrentHashMap}.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * OAuth2Config config = OAuth2Config.builder()
 *     .clientId("your-client-id")
 *     .clientSecret("your-client-secret")
 *     .tokenUrl("https://www.linkedin.com/oauth/v2/accessToken")
 *     .build();
 *
 * OAuth2Provider provider = OAuth2Provider.getInstance(config);
 * String accessToken = provider.getAccessToken();
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-module1-basics?view=li-lts-2025-01#step-2-generate-an-access-token"><a/>
 * </pre>
 *
 * @see AuthenticationProvider
 * @see OAuth2Config
 */
public class OAuth2Provider implements AuthenticationProvider {
  private static final Logger LOGGER = Logger.getLogger(OAuth2Provider.class.getName());

  /**
   * Cache of provider instances keyed by their configurations.
   * Uses ConcurrentHashMap to ensure thread-safe access to provider instances.
   */
  private static final ConcurrentHashMap<OAuth2Config, OAuth2Provider> INSTANCES = new ConcurrentHashMap<>();

  /**
   * The OAuth 2.0 configuration for this provider instance.
   */
  private final OAuth2Config config;

  /**
   * The HTTP client used to make requests to LinkedIn's API.
   */
  protected final HttpClient httpClient;

  /**
   * The current OAuth 2.0 token. Volatile ensures visibility across threads.
   */
  private volatile OAuth2Token currentToken;


  /**
   * Private constructor enforcing singleton pattern per configuration.
   *
   * @param config the OAuth 2.0 configuration for this provider
   */
  protected OAuth2Provider(OAuth2Config config, HttpClient httpClient) {
    this.config = config;
    this.httpClient = httpClient;
  }

  /**
   * Factory method to obtain an OAuth2Provider instance for a given configuration.
   * If an instance already exists for the provided configuration, it will be returned.
   * Otherwise, a new instance will be created.
   *
   * @param config the OAuth 2.0 configuration to use
   * @param httpClient the HTTP client to use for making requests
   * @return an OAuth2Provider instance for the given configuration
   * @throws NullPointerException if config is null
   */
  public static synchronized OAuth2Provider getInstance(OAuth2Config config, HttpClient httpClient) {
    return INSTANCES.computeIfAbsent(config, cfg -> new OAuth2Provider(cfg, httpClient));
  }

  /**
   * {@inheritDoc}
   *
   * <p>This implementation ensures a valid token is available before returning.
   * If the current token is expired or null, a new token will be obtained
   * through authentication.</p>
   *
   * <p>This method is synchronized to prevent multiple concurrent authentication
   * attempts.</p>
   */
  @Override
  public synchronized String getAccessToken() throws AuthenticationException {
    if (!isTokenValid()) {
      authenticate();
    }
    return currentToken.getAccessToken();
  }

  /**
   * Checks if the current OAuth 2.0 token is valid.
   */
  protected boolean isTokenValid() {
    return currentToken != null && !currentToken.isExpired();
  }

  /**
   * Performs OAuth 2.0 client credentials flow authentication.
   * Makes an HTTP POST request to the configured token URL with client credentials
   * to obtain a new access token.
   *
   * @throws AuthenticationException if authentication fails due to invalid credentials,
   *         network issues, or invalid server response
   */
  private void authenticate() throws AuthenticationException {
    try {

      String url = config.getTokenUrl();
      Map<String, String> headers = new HashMap<>();
      headers.put(CONTENT_TYPE, APPLICATION_FORM_URLENCODED);
      headers.put(ACCEPT, APPLICATION_JSON);

      String formBody = String.format(
          "grant_type=client_credentials" + QUERY_PARAM_SEPARATOR+ "client_id=%s" +  QUERY_PARAM_SEPARATOR + "client_secret=%s",
          encodeURIComponent(config.getClientId()),
          encodeURIComponent(config.getClientSecret())
      );

      String response = httpClient.executeRequest(url, HttpMethod.POST, headers, formBody);
      TokenInfo tokenInfo = ObjectMapperUtil.fromJson(response, TokenInfo.class);
      currentToken = new OAuth2Token(tokenInfo.getAccessToken(), tokenInfo.getExpiresIn());
    } catch (IOException e) {
      String errorMessage = String.format(
          "Failed to authenticate with LinkedIn API. Client ID: %s, Error: %s", config.getClientId(), e.getMessage());
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new AuthenticationException(errorMessage, e);
    } catch (JsonDeserializationException e) {
      String errorMessage = String.format(
          "Failed to deserialize authentication response. Client ID: %s, Response: %s",
          config.getClientId(), e.getMessage());
      LOGGER.log(Level.SEVERE, errorMessage, e);
      throw new AuthenticationException(errorMessage, e);
    } catch (LinkedInApiException e) {
      String errorMessage = String.format(
          "Failed to authenticate with LinkedIn API. Client ID: %s, Response Code: %d, Response: %s",
          config.getClientId(), e.getStatusCode(), e.getErrorBody());
      LOGGER.severe(errorMessage);
      throw new AuthenticationException("Authentication failed with status: " + e.getStatusCode());
    }
  }

  /**
   * URL encodes a value using UTF-8 encoding.
   *
   * @param value the string to encode
   * @return the URL encoded string
   * @throws RuntimeException if encoding fails
   */
  private String encodeURIComponent(String value) {
    try {
      return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (Exception e) {
      throw new RuntimeException("Failed to encode URL parameter: " + value, e);
    }
  }
}

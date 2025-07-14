package com.linkedin.sdk.lts.internal.client.linkedinclient;

import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.exception.TransientLinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;

import com.linkedin.sdk.lts.internal.util.LogRedactor;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;
import lombok.NonNull;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;

/**
 * LinkedInHttpClient is an implementation of HttpClient that handles HTTP requests
 * to LinkedIn's API endpoints. It supports various HTTP methods and manages headers,
 * request bodies, and response handling.
 */
public class LinkedInHttpClient implements HttpClient {
  private static final Logger LOGGER = Logger.getLogger(LinkedInHttpClient.class.getName());
  private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
  private static final int DEFAULT_READ_TIMEOUT = 30000;

  private final RetryConfig retryConfig;

  public LinkedInHttpClient(RetryConfig retryConfig) {
    this.retryConfig = retryConfig;
  }

  /**
   * Executes an HTTP request to the specified URL using the given method, headers, and body.
   *
   * @param url     the URL to send the request to
   * @param method  the HTTP method to use (e.g., GET, POST)
   * @param headers the HTTP headers to include in the request
   * @param body    the request body (optional, can be null for GET requests)
   * @return the response body as a string
   * @throws IOException           if an I/O error occurs during the request
   * @throws LinkedInApiException if the API returns an error response
   */
  @Override
  public String executeRequest(@NonNull String url,@NonNull HttpMethod method, Map<String, String> headers, String body)
      throws IOException, LinkedInApiException {
    long backoff = retryConfig.getInitialBackoffMillis();
    LinkedInApiException lastException = null;

    for (int attempt = 0; attempt <= retryConfig.getMaxRetries(); attempt++) {
      try {
        return executeWithErrorHandling(url, method, headers, body);
      } catch (TransientLinkedInApiException e) {
        lastException = e;

        // If this was our last attempt, don't sleep
        if (attempt == retryConfig.getMaxRetries()) {
          LOGGER.severe(String.format("Max retries reached (%d). Last error: %s",
              retryConfig.getMaxRetries(), e.getMessage()));
          throw e;
        }

        try {
          LOGGER.warning(String.format("Transient error occurred (attempt %d/%d). Retrying in %d ms. Error: %s",
              attempt + 1, retryConfig.getMaxRetries(), backoff, e.getMessage()));
          Thread.sleep(backoff);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
          throw e;
        }

        backoff = Math.min(
            (long) (backoff * retryConfig.getBackoffMultiplier()),
            retryConfig.getMaxBackoffMillis()
        );
      }
    }

    // This should never happen due to the throw in the loop, but adding for completeness
    throw lastException;
  }

  /**
   * @param url     the URL to send the request to
   * @param method  the HTTP method to use (e.g., GET, POST)
   * @param headers the HTTP headers to include in the request
   * @param body    the request body (optional, can be null for GET requests)
   * @return the response body as a string
   * @throws IOException           if an I/O error occurs during the request
   * @throws LinkedInApiException if the API returns an error response
   * @throws TransientLinkedInApiException if a transient error occurs
   */
  private String executeWithErrorHandling(@NonNull String url,@NonNull HttpMethod method, Map<String, String> headers, String body)
      throws LinkedInApiException {
    try {
      LOGGER.info(LogRedactor.redact(String.format("Sending %s request to %s with body %s", method, url, body)));

      HttpsURLConnection connection = createConnection(new URL(url), method);
      setHeaders(connection, headers);

      if (body != null && !method.equals(HttpMethod.GET)) {
        writeRequestBody(connection, body);
      }

      return getResponseBody(connection);
    } catch (IOException e) {
      throw new TransientLinkedInApiException(500, "Network error occurred", e.getMessage());
    }
  }

  /**
   * Creates an HttpsURLConnection for the specified URL and HTTP method.
   *
   * @param url    the URL to connect to
   * @param method the HTTP method to use
   * @return a configured HttpsURLConnection instance
   * @throws IOException if an I/O error occurs while opening the connection
   */
  protected HttpsURLConnection createConnection(URL url, HttpMethod method) throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod(method.getValue());
    connection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
    connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
    connection.setDoInput(true);
    connection.setDoOutput(!method.equals(HttpMethod.GET));
    connection.setInstanceFollowRedirects(false);
    return connection;
  }

  /**
   * Sets the HTTP headers for the connection.
   *
   * @param connection the HttpsURLConnection to set headers on
   * @param headers    a map of header names and values
   */
  private void setHeaders(HttpsURLConnection connection, Map<String, String> headers) {
    if (headers != null) {
      headers.forEach(connection::setRequestProperty);
    }
  }

  /**
   * Writes the request body to the connection's output stream.
   *
   * @param connection the HttpsURLConnection to write the body to
   * @param body       the request body as a string
   * @throws IOException if an I/O error occurs while writing the body
   */
  private void writeRequestBody(HttpsURLConnection connection, String body) throws IOException {
    byte[] requestBodyBytes = body.getBytes(StandardCharsets.UTF_8);
    connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(requestBodyBytes.length));

    try (OutputStream os = connection.getOutputStream()) {
      os.write(requestBodyBytes);
      os.flush();
    }
  }

  /**
   * Reads the response body from the connection and handles errors if the response code indicates failure.
   *
   * @param connection the HttpsURLConnection to read the response from
   * @return the response body as a string
   * @throws IOException           if an I/O error occurs while reading the response
   * @throws LinkedInApiException if the API returns an error response
   */
  private String getResponseBody(HttpsURLConnection connection) throws IOException, LinkedInApiException {
    int responseCode = connection.getResponseCode();
    InputStream inputStream = HttpStatusCategory.SUCCESS.matches(responseCode)
        ? connection.getInputStream()
        : connection.getErrorStream();

    String response = readStream(inputStream);
    LOGGER.info(LogRedactor.redact(String.format("Response body: %s", response)));

    if (TransientLinkedInApiException.isTransient(responseCode)) {
      String errorMessage = "HTTP error " + responseCode;
      LOGGER.severe(errorMessage);
      throw new TransientLinkedInApiException(responseCode, response, errorMessage);
    } else if(!HttpStatusCategory.SUCCESS.matches(responseCode)) {
      String errorMessage = "HTTP error " + responseCode + ": " + response;
      LOGGER.severe(errorMessage);
      throw new LinkedInApiException(responseCode, response, errorMessage);
    }

    return response;
  }

  /**
   * Reads the content of an InputStream and returns it as a String.
   *
   * @param inputStream the InputStream to read from
   * @return the content of the InputStream as a String
   * @throws IOException if an I/O error occurs while reading the stream
   */
  private String readStream(InputStream inputStream) throws IOException {
    if (inputStream == null) return "";

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      return reader.lines().collect(java.util.stream.Collectors.joining());
    }
  }
}
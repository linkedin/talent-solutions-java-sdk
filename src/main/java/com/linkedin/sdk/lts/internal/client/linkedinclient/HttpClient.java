package com.linkedin.sdk.lts.internal.client.linkedinclient;

import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import java.io.IOException;
import java.util.Map;

/**
 * HttpClient is an interface for executing HTTP requests to LinkedIn's API endpoints.
 * It abstracts the details of making HTTP requests and handling responses.
 */
public interface HttpClient<T> {
  /**
   * Executes an HTTP request
   *
   * @param url the URL to send the request to
   * @param method the HTTP method to use
   * @param headers the HTTP headers to include
   * @param body the request body (optional)
   * @param responseType the class type of the expected response
   * @return the response body as a string
   * @throws IOException if an I/O error occurs
   * @throws LinkedInApiException if the API returns an error response
   * @throws JsonDeserializationException if there is an error deserializing the response
   */
  APIResponse<T> executeRequest(String url, HttpMethod method, Map<String, String> headers,
      String body, Class<T> responseType)
      throws IOException, LinkedInApiException, JsonDeserializationException;
}
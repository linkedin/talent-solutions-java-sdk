package com.linkedin.sdk.lts.jobs.client.linkedinclient;

import com.linkedin.sdk.lts.jobs.exception.LinkedInApiException;
import com.linkedin.sdk.lts.jobs.model.response.common.HttpMethod;
import java.io.IOException;
import java.util.Map;

public interface HttpClient {
  /**
   * Executes an HTTP request
   *
   * @param url the URL to send the request to
   * @param method the HTTP method to use
   * @param headers the HTTP headers to include
   * @param body the request body (optional)
   * @return the response body as a string
   * @throws IOException if an I/O error occurs
   * @throws LinkedInApiException if the API returns an error response
   */
  String executeRequest(String url, HttpMethod method, Map<String, String> headers, String body)
      throws IOException, LinkedInApiException;
}
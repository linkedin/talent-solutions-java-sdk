package com.linkedin.sdk.lts.jobs.model.response.common;

/**
 * Enum representing standard HTTP methods used in API requests.
 * <p>
 * Includes common HTTP methods:
 * - GET: Retrieve a resource
 * - POST: Create a new resource
 * - PUT: Update/Replace an existing resource
 * - DELETE: Remove a resource
 * - PATCH: Partially update a resource
 * - HEAD: Retrieve headers only
 * - OPTIONS: Get supported methods/options
 */
public enum HttpMethod {

  /**
   * HTTP GET method for retrieving resources
   */
  GET("GET"),

  /**
   * HTTP POST method for creating new resources
   */
  POST("POST"),

  /**
   * HTTP PUT method for updating/replacing existing resources
   */
  PUT("PUT"),

  /**
   * HTTP DELETE method for removing resources
   */
  DELETE("DELETE");

  private final String method;

  HttpMethod(String method) {
    this.method = method;
  }

  /**
   * Returns the string representation of the HTTP method.
   *
   * @return the HTTP method as a string
   */
  public String getValue() {
    return this.method;
  }
}

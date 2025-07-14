package com.linkedin.sdk.lts.api.exception;

import com.linkedin.sdk.lts.api.model.response.common.HttpStatusCategory;


public class TransientLinkedInApiException extends LinkedInApiException {
  public TransientLinkedInApiException(int statusCode, String message, String details) {
    super(statusCode, message, details);
  }

  public static boolean isTransient(int statusCode) {
    return HttpStatusCategory.SERVER_ERROR.matches(statusCode);
  }
}

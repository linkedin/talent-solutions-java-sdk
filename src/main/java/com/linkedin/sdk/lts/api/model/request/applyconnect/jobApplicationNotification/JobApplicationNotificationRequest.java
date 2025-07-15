package com.linkedin.sdk.lts.api.model.request.applyconnect.jobApplicationNotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a notification request for job application actions.
 * This class defines the structure for notifications sent when specific actions are taken on job applications.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/sync-job-application-feedback?view=li-lts-2025-04">LinkedIn Apply Connect Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationNotificationRequest {

  /**
   * The LinkedIn job application id for which an action has been taken.
   */
  private String jobApplicationId;

  /**
   * The action which was taken on the given job application.
   * Possible values: APPLICATION_VIEWED, RESUME_DOWNLOADED, or APPLICATION_REJECTED
   */
  private JobApplicationAction action;

  /**
   * Timestamp for when the action was taken. Milliseconds since epoch.
   */
  private Long performedAt;

  /**
   * Must be of the form urn:li:organization:12345.
   * Required if received during customer enablement from Customer Configuration Plugin.
   */
  private String integrationContext;
}

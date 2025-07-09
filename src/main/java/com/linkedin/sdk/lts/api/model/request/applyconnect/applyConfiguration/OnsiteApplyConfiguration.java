package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the on-site apply configuration for a job posting on LinkedIn.
 * Contains webhook URL configuration for receiving job applications and optional screening questions.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/onsite-apply-config-schema?view=li-lts-2025-04">LinkedIn Apply Connect Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnsiteApplyConfiguration {

  /**
   * Required URL where job applications from LinkedIn will be received in near-realtime.
   * Any URL provided must be capable of passing the Webhook Endpoint Validation Flow.
   */
  private String jobApplicationWebhookUrl;

  /**
   * Optional questions configuration for the job application process.
   * If not specified, only basic default contact information questions will be asked
   * during the application process (name, email, and phone number).
   */
  private SimpleTalentQuestions questions;
}
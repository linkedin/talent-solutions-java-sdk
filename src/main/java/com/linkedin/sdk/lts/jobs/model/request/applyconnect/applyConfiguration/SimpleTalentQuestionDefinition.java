package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a custom question definition for talent applications.
 * Controls how questions are displayed, validated, and processed in the job application.
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
public class SimpleTalentQuestionDefinition {

  /**
   * A unique identifier for this custom question.
   * Used by partner systems to associate answers when a job application is delivered.
   */
  private String partnerQuestionIdentifier;

  /**
   * The details needed for a member to answer a question in any form.
   * These question details instruct the front-end on rendering inputs and validation.
   */
  private SimpleQuestionDetails questionDetails;

  /**
   * Whether the question is required to complete the job application.
   */
  private Boolean required;

  /**
   * The label text to describe this question.
   * Supports HTML formatting: ul, li, b, i, a, and p tags.
   */
  private String questionText;

  /**
   * Brief additional information available to the person being asked the question.
   */
  private String helpMessage;

  /**
   * Full additional information available to the person being asked the question.
   */
  private String expandedHelpMessage;

  /**
   * Defines when this question should be displayed based on a prerequisite question's response.
   * Note: One CustomQuestionSet can only have one prerequisite question.
   */
  private PrerequisiteQuestionResponse prerequisiteQuestionResponse;
}
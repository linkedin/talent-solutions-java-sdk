package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents configuration details for text questions.
 * Contains length constraints, suggested value, and preferred input component type.
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
public class TextQuestionDetails {

  /**
   * The suggested answer that is prefilled for the applicant being asked the question.
   */
  private String suggestedValue;

  /**
   * The minimum text length for the answer.
   * Defaults to 0.
   */
  private Integer minLength;

  /**
   * The maximum text length for the answer.
   * The value should be greater than 0.
   */
  private Integer maxLength;

  /**
   * Indicates whether a one-line or multi-line text input should be used.
   * Should be TEXT or MULTILINE_TEXT.
   */
  private FormComponent preferredFormComponent;
}
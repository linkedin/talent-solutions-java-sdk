package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents configuration details for integer number questions.
 * Contains range constraints and suggested default value.
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
public class IntegerQuestionDetails {

  /**
   * The suggested answer that is prefilled for the applicant being asked the question.
   */
  private Integer suggestedValue;

  /**
   * The minimum value that is allowed for a decimal answer.
   * Defaults to "0".
   */
  private Integer minValue;

  /**
   * The maximum value allowed for an integer answer.
   */
  private Integer maxValue;
}
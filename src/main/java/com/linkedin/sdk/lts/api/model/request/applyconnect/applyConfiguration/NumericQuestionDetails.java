package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents details for numeric questions, supporting either decimal or integer inputs.
 * The fields decimalQuestionDetails and integerQuestionDetails are mutually exclusive.
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
public class NumericQuestionDetails {

  /**
   * Configuration for decimal number questions.
   * Must be null if integerQuestionDetails is provided.
   */
  private DecimalQuestionDetails decimalQuestionDetails;

  /**
   * Configuration for integer number questions.
   * Must be null if decimalQuestionDetails is provided.
   */
  private IntegerQuestionDetails integerQuestionDetails;

  /**
   * The favorable numeric answer value for this question.
   * Only present for questions with a preferred/required qualification.
   */
  private String favorableNumericAnswer;
}
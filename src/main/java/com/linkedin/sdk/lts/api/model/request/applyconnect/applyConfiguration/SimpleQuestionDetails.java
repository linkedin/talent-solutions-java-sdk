package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the specific details of a question in a job application.
 * Only one type of question details can be set at a time - all other fields must be null.
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
public class SimpleQuestionDetails {

  /**
   * Represents a question where a date range must be provided as the answer.
   * If present, all other fields must be null.
   */
  private DateRangeQuestionDetails dateRangeQuestionDetails;

  /**
   * Represents a question where a document must be provided as the answer.
   * If present, all other fields must be null.
   */
  private DocumentQuestionDetails documentQuestionDetails;

  /**
   * Represents a question where a multiple-choice selection must be provided as the answer.
   * If present, all other fields must be null.
   */
  private MultipleChoiceQuestionDetails multipleChoiceQuestionDetails;

  /**
   * Represents a question where a number must be provided as the answer.
   * If present, all other fields must be null.
   */
  private NumericQuestionDetails numericQuestionDetails;

  /**
   * Represents a question where text must be provided as the answer.
   * If present, all other fields must be null.
   */
  private TextQuestionDetails textQuestionDetails;
}
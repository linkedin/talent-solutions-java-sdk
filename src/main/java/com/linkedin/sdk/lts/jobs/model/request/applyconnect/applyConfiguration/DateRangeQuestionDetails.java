package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents date range question details for job application questions.
 * Controls how date range inputs are displayed and labeled in the application UI.
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
public class DateRangeQuestionDetails {

  /**
   * The text that will be used to describe the start date input.
   * Defaults to "From" if not specified.
   */
  private String startLabel;

  /**
   * The text that will be used to describe the end date input.
   * Defaults to "To" if not specified.
   */
  private String endLabel;

  /**
   * The text that will be used to describe the input that controls end date visibility.
   * For example, "I currently work here" checkbox that hides the end date when checked.
   */
  private String hideEndDateLabel;
}
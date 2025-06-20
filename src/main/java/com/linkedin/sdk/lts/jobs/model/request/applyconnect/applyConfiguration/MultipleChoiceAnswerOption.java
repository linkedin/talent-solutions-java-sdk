package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an answer option for multiple choice questions.
 * Contains both machine-readable and human-readable values.
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
public class MultipleChoiceAnswerOption {

  /**
   * Machine-parsable name to refer to this particular answer.
   */
  private String symbolicName;

  /**
   * The display value for this multiple choice answer.
   */
  private String displayValue;
}
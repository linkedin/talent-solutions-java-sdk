package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the conditions under which a dependent question should be displayed
 * based on the response to a prerequisite multiple-choice radio button question.
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
public class PrerequisiteQuestionResponse {

  /**
   * The prerequisite question that triggers the display of this question.
   * Must be a multiple choice question with RADIO_BUTTONS as form component.
   */
  private PrerequisiteQuestionIdentifier prerequisiteQuestion;

  /**
   * The required response to the prerequisite question that triggers the display of this question.
   */
  private PrerequisiteAnswerValue prerequisiteResponse;
}
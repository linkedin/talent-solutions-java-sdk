package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents configuration details for multiple choice questions in job applications.
 * Supports both single and multiple selection options with various display formats.
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
public class MultipleChoiceQuestionDetails {

  /**
   * Array of possible answers to this question.
   */
  private MultipleChoiceAnswerOption[] choices;

  /**
   * The symbolic name for the default answer value from the array of choices.
   */
  private String defaultValueSymbolicName;

  /**
   * Contains the favorable multiple choice answer value for this question.
   * Only present for questions with a preferred/required qualification.
   */
  private FavorableMultipleChoiceAnswer favorableMultipleChoiceAnswer;

  /**
   * Indicates the UI component to display this question (radio buttons, dropdown, etc).
   */
  private FormComponent preferredFormComponent;

  /**
   * Indicates whether multiple options can be selected when FormComponent is CHECKBOXES.
   * Defaults to false.
   */
  private Boolean selectMultiple;
}
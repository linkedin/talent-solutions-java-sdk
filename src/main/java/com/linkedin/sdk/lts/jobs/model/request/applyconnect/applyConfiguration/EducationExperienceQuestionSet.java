package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a set of education experience questions that can be repeated in a job application.
 * Controls the requirements and limits for education experience entries.
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
public class EducationExperienceQuestionSet {

  /**
   * Minimum required education experience entries.
   * If no minRequired is specified, this field will default to 0.
   */
  private Integer minRequired;

  /**
   * The maximum number of times this question set can be asked.
   * Acceptable range: [1, 3]
   * If no repeatLimit is specified, this field will default to 3.
   */
  private Integer repeatLimit;

  /**
   * Captures whether the "School" question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement schoolQuestionRequirement;

  /**
   * Captures whether the "Employment Dates" question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement datesAttendedQuestionRequirement;

  /**
   * Captures whether the "City" question should be asked.
   * Defaults to OPTIONAL.
   */
  private QuestionRequirement cityQuestionRequirement;

  /**
   * Captures whether the "Degree" question should be asked.
   * Defaults to OPTIONAL.
   */
  private QuestionRequirement degreeQuestionRequirement;

  /**
   * Captures whether the "Field of Study" question should be asked.
   * Defaults to OPTIONAL.
   */
  private QuestionRequirement fieldOfStudyQuestionRequirement;

  /**
   * Additional repeatable custom questions to be included within the Education Experience question set.
   */
  private List<SimpleTalentQuestionDefinition> customQuestions;
}
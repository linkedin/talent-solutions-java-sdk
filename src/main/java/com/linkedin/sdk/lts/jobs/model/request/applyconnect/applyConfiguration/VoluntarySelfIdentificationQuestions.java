package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents Equal Employment Opportunity Commission (EEOC) voluntary self-identification questions
 * for a job application. Controls various EEOC question requirements.
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
public class VoluntarySelfIdentificationQuestions {

  /**
   * Captures whether the EEOC Disability question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement disabilityQuestionRequirement;

  /**
   * Captures whether the EEOC Gender question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement genderQuestionRequirement;

  /**
   * Captures whether the EEOC Race question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement raceQuestionRequirement;

  /**
   * Captures whether the EEOC Veteran Status question should be asked.
   * Defaults to REQUIRED.
   */
  private QuestionRequirement veteranStatusQuestionRequirement;

  /**
   * Additional repeatable custom questions to be included within the
   * Voluntary Self Identification question set.
   */
  private List<SimpleTalentQuestionDefinition> customQuestions;
}
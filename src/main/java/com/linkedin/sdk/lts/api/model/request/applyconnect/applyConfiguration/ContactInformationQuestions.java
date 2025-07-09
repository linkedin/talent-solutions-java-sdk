package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the contact information questions configuration for a job application.
 * Controls which contact information fields should be collected from applicants.
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
public class ContactInformationQuestions {

  /**
   * Captures whether middle name should be collected.
   * Defaults to SKIP.
   */
  private QuestionRequirement middleNameQuestionRequirement;

  /**
   * Captures whether cell phone number should be collected.
   * Defaults to OPTIONAL.
   */
  private QuestionRequirement cellphoneNumberQuestionRequirement;

  /**
   * Captures whether location should be collected.
   * Defaults to SKIP.
   */
  private QuestionRequirement locationQuestionRequirement;

  /**
   * Custom questions to be included within Contact Information section.
   */
  private List<SimpleTalentQuestionDefinition> customQuestions;
}
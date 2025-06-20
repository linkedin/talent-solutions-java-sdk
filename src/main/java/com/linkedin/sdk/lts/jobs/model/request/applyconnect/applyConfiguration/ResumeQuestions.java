package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the resume questions configuration for a job application.
 * Controls resume file requirements and additional custom questions.
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
public class ResumeQuestions {

  /**
   * Captures whether a resume file needs to be provided.
   * Max file size: 2 MB
   * Accepted file types: "doc", "docx", "pdf"
   * Defaults to OPTIONAL.
   */
  private QuestionRequirement resumeQuestionRequirement;

  /**
   * Custom questions to be included within Resume Question section.
   */
  private List<SimpleTalentQuestionDefinition> customQuestions;
}
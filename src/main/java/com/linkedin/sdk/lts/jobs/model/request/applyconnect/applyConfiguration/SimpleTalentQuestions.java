package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents all possible question sections that can be included in a job application.
 * Each section is optional and if not specified, the corresponding questions will not be asked
 * during the application process.
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
public class SimpleTalentQuestions {

  /**
   * Contact Information question section.
   * If null, only mandatory contact information questions (First Name, Last Name, and Email) will be collected.
   */
  private ContactInformationQuestions contactInformationQuestions;

  /**
   * Resume question section.
   * If null, no resume questions will be asked.
   */
  private ResumeQuestions resumeQuestions;

  /**
   * Cover letter question section.
   * If null, no cover letter questions will be asked.
   */
  private CoverLetterQuestions coverLetterQuestions;

  /**
   * Work experience questions section.
   * If null, no work questions will be asked.
   */
  private WorkQuestions workQuestions;

  /**
   * Education question section.
   * If null, no education questions will be asked.
   */
  private EducationQuestions educationQuestions;

  /**
   * Voluntary Self Identification question section.
   * If null, no voluntary self identification questions will be asked.
   */
  private VoluntarySelfIdentificationQuestions voluntarySelfIdentificationQuestions;

  /**
   * Additional custom questions section.
   * Questions will be rendered in the "Additional Questions" section of the Job Application UI.
   * If null, no additional questions will be asked.
   */
  private AdditionalQuestions additionalQuestions;
}
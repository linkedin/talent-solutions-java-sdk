package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a set of custom questions that can be repeated in a job application.
 * Controls the custom questions and their repeat limits.
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
public class CustomQuestionSet {

  /**
   * The list of 1 or more custom questions that are a part of this custom question set.
   * The ordering of these questions in this list will be maintained when rendering on UI.
   */
  private List<SimpleTalentQuestionDefinition> questions;

  /**
   * The maximum number of times this question set can be asked.
   * Valid range: [1, 10]
   * If no value is specified then repeatLimit will default to 1.
   */
  private Integer repeatLimit;
}
package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents favorable numeric ranges for a numeric question.
 * Used to define minimum and maximum acceptable values for numeric answers.
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
public class FavorableNumericAnswer {

  /**
   * The minimum inclusive value that will be considered favorable.
   * Example: 5.0 for "Minimum 5 years of experience".
   * Null means negative infinity.
   */
  private Float favorableFloor;

  /**
   * The maximum inclusive value that will be considered favorable.
   * Null means infinity.
   */
  private Float favorableCeiling;
}
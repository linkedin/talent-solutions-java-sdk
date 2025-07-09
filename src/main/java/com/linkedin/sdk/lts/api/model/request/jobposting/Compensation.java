package com.linkedin.sdk.lts.api.model.request.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents compensation information for a job posting in the LinkedIn Job Posting API.
 * This class defines the structure for salary and other compensation details,
 * including amount ranges and payment periods.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * <p>Validation Rules:</p>
 * <ul>
 *   <li>The value (range) field is required and cannot be null</li>
 *   <li>The period field is required and must be a valid {@link CompensationPeriod} value</li>
 *   <li>The type field is required and must be a valid {@link CompensationType} value</li>
 *   <li>The amount field is optional but must follow valid money amount format if provided</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Compensation {

  /**
   * The fixed compensation amount.
   * Optional field that must follow valid money amount format if provided.
   * Use this when the compensation is a fixed amount rather than a range.
   *
   * @see MoneyAmount
   */
  private MoneyAmount amount;

  /**
   * The compensation range with start and end amounts.
   * Required field that cannot be null.
   * Both start and end amounts must be valid money amounts.
   *
   * @see MoneyAmountRange
   */
  private MoneyAmountRange value;

  /**
   * The period for which the compensation is paid.
   * Required field.
   * @see CompensationPeriod
   */
  private CompensationPeriod period;

  /**
   * The type of compensation being offered.
   * Required field.
   * @see CompensationType
   */
  private CompensationType type;

}


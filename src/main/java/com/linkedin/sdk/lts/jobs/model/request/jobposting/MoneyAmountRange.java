package com.linkedin.sdk.lts.jobs.model.request.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a range of monetary values in the LinkedIn Job Posting API.
 * This class is typically used to specify salary ranges or compensation ranges
 * for job postings.
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
 *   <li>Both start and end fields are required and cannot be null</li>
 *   <li>Both start and end must be valid {@link MoneyAmount} objects</li>
 *   <li>Both amounts should use the same currency code</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyAmountRange {

  /**
   * The lower bound of the monetary range.
   * Required field that cannot be null.
   * Must be a valid {@link MoneyAmount} object.
   *
   * @see MoneyAmount
   */
  private MoneyAmount start;

  /**
   * The upper bound of the monetary range.
   * Required field that cannot be null.
   * Must be a valid {@link MoneyAmount} object.
   *
   * @see MoneyAmount
   */
  private MoneyAmount end;

}

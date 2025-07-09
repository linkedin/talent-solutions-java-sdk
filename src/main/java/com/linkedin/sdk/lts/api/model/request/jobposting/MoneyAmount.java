package com.linkedin.sdk.lts.api.model.request.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a monetary amount with its currency in the LinkedIn Job Posting API.
 * This class provides a structure for representing precise monetary values
 * with their associated currency codes.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyAmount {

  /**
   * The ISO currency code for the monetary amount.
   * Required field that must be a valid three-letter currency code
   * (e.g., "USD", "EUR", "INR").
   * Cannot be null or empty.
   */
  private CurrencyCode currencyCode;

  /**
   * The monetary amount as a string representation of a real number.
   * Required field that must be a valid numeric value.
   * Cannot be null or empty.
   * Examples of valid amounts: "75000.00", "100000", "80500.50"
   */
  private String amount;

}

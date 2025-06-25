package com.linkedin.sdk.lts.jobs.model.request.jobposting;

/**
 * Defines the possible types of compensation in the LinkedIn Job Posting API.
 * These values represent different forms of monetary and non-monetary
 * compensation that may be offered as part of a job's total compensation package.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-04">LinkedIn Job Posting Documentation</a>
 */
public enum CompensationType {

  /**
   * Represents a fixed base salary for the job position.
   */
  BASE_SALARY,

  /**
   * Represents additional compensation in the form of tips.
   */
  TIPS,

  /**
   * Represents compensation based on commission, typically in sales roles.
   */
  COMMISSION,

  /**
   * Represents a share of the company's profits distributed to employees.
   */
  PROFIT_SHARING,

  /**
   * Represents compensation in the form of stock options, allowing employees to purchase company stock at a set price.
   */
  STOCK_OPTIONS,

  /**
   * Represents compensation in the form of company stock, which may include shares or equity.
   */
  STOCK,

  /**
   * Represents a bonus payment, which is typically a one-time or periodic additional payment.
   */
  BONUS,

  /**
   * Represents a sign-on bonus, which is a one-time payment made to new employees upon joining the company.
   */
  SIGN_ON_BONUS,

  /**
   * Represents compensation for overtime work, which is typically paid at a higher rate than regular hours.
   */
  OVER_TIME,

  /**
   * Represents any other form of compensation that does not fit into the predefined categories.
   */
  OTHER
}

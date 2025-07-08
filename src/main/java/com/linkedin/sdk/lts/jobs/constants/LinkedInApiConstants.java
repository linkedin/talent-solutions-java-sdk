package com.linkedin.sdk.lts.jobs.constants;

public class LinkedInApiConstants {

  /**
   * API VERSION 2025_04 for LinkedIn's Job Posting APIs.
   */
  public static final String API_VERSION_2025_04 = "202504";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints.
   */
  public static final String BASE_URL = "https://api.linkedin.com/v2/";

  /**
   * The base URL for LinkedIn's Job Posting APIs versioned endpoints.
   */
  public static final String BASE_URL_VERSIONED = "https://api.linkedin.com/rest/";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job postings.
   */
  public static final String JOB_POSTING_BASE_URL = BASE_URL_VERSIONED + "simpleJobPostings";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job posting tasks.
   */
  public static final String JOB_TASK_STATUS_BASE_URL = BASE_URL_VERSIONED + "simpleJobPostingTasks";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job posting status.
   */
  public static final String JOB_STATUS_BASE_URL = BASE_URL + "jobPostingStatus";

  /**
   * The URL for LinkedIn's OAuth 2.0 authorization endpoint.
   */
  public static final String LINKEDIN_ACCESS_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";

  /**
    * The base URL for LinkedIn's Provisioned Applications API endpoints.
   */
   public static final String PROVISIONING_APPLICATION_BASE_URL = "https://api.linkedin.com/v2/provisionedApplications/";

   /**
   * The header for LinkedIn X-REST.li protocol versioning.
   */
  public static final String X_REST_LI_PROTOCOL_VERSION = "x-restli-protocol-version";

  /**
   * The value for LinkedIn X-REST.li protocol versioning 2.0.0.
   */
  public static final String X_REST_LI_PROTOCOL_VERSION_VALUE_2_0_0 = "2.0.0";

  /**
   *  The header for LinkedIn API versioning.
   */
  public static final String LINKEDIN_VERSION = "LinkedIn-Version";

  /**
   * The URL for LinkedIn's PROVISIONED Hiring Contracts API endpoints for P4P Jobs.
   */
  public static final String PROVISIONING_HIRING_CONTRACT_URL = BASE_URL_VERSIONED + "provisionedHiringContracts?action=setup";

  /**
   * The base URL for LinkedIn's Partner Job Reports API endpoints for P4P Jobs.
   */
  public static final String PARTNER_JOB_REPORTS_BASE_URL = BASE_URL_VERSIONED + "partnerJobReports";

  /**
   * The base URL for LinkedIn's Partner Budget Reports for P4P Jobs.
   */
  public static final String PARTNER_BUDGET_REPORTS_BASE_URL = BASE_URL_VERSIONED + "partnerBudgetReports";

  /**
   * Class containing constants for LinkedIn P4P Reports API query params constant values.
   */
  public static class P4P_REPORTS_API_CONSTANTS {
    public static final String COUNT = "count";
    public static final String DATE_RANGE = "dateRange";
    public static final String DAY = "day";
    public static final String END = "end";
    public static final String IDS = "ids";
    public static final String LIST = "List";
    public static final String MONTH = "month";
    public static final String PARTNER_CONTRACT_ID = "partnerContractId";
    public static final String QUERY = "q";
    public static final String START = "start";
    public static final String YEAR = "year";
  }

  /**
   * Class containing constants for LinkedIn Provisioning API query params constant values.
   */
  public static class PROVISIONING_API_CONSTANTS {
    public static final String CREDENTIALS_BY_UNIQUE_FOREIGN_ID = "credentialsByUniqueForeignId";
    public static final String QUERY = "q";
    public static final String UNIQUE_FOREIGN_ID = "uniqueForeignId";
  }
}

package com.linkedin.sdk.lts.jobs.constants;

public class LinkedInApiConstants {

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints.
   */
  public static final String BASE_URL = "https://api.linkedin.com/v2/";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job postings.
   */
  public static final String JOB_POSTING_BASE_URL = BASE_URL + "simpleJobPostings";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job posting tasks.
   */
  public static final String JOB_TASK_STATUS_BASE_URL = BASE_URL + "simpleJobPostingTasks";

  /**
   * The base URL for LinkedIn's Job Posting APIs v2 endpoints related to job posting status.
   */
  public static final String JOB_STATUS_BASE_URL = BASE_URL + "jobPostingStatus";
}

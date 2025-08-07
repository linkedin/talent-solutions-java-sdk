package com.linkedin.sdk.lts.api.model.request.JobPosting;

import com.linkedin.sdk.lts.api.model.request.jobposting.JobPosting;
import com.linkedin.sdk.lts.api.model.request.jobposting.JobPostingOperationType;

import org.testng.annotations.Test;

import static com.linkedin.sdk.lts.internal.constants.LinkedInCommonConstants.*;
import static org.testng.Assert.*;

public class JobPostingTest {

  private static final String COMPANY_ID = "12345";
  private static final String COMPANY_ID_NEW = "123456";
  private static final String EXTERNAL_JOB_POSTING_ID = "123";
  private static final String TITLE = "Software Engineer";
  private static final String DESCRIPTION = "Job description";

  @Test
  public void testIntegrationContextSetup() {
    JobPosting jobPosting = JobPosting.builder()
        .externalJobPostingId(EXTERNAL_JOB_POSTING_ID)
        .jobPostingOperationType(JobPostingOperationType.CREATE)
        .title(TITLE)
        .description(DESCRIPTION)
        .build();

    assertEquals(jobPosting.getCompany(), null);

    jobPosting.setCompanyId(COMPANY_ID);
    // Verify companyId and integrationContext
    assertEquals(jobPosting.getCompany(), LINKEDIN_ORGANIZATION_URN_FORMAT + COMPANY_ID);

    // Update companyId and verify integrationContext
    jobPosting.setCompanyId(COMPANY_ID_NEW);
    assertEquals(jobPosting.getCompany(), LINKEDIN_ORGANIZATION_URN_FORMAT + COMPANY_ID_NEW);

    jobPosting.setCompanyId(null);
    assertEquals(jobPosting.getCompany(), null);

    jobPosting.setCompanyId("");
    assertEquals(jobPosting.getCompany(), null);
  }
}
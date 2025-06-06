package com.linkedin.sdk.lts.jobs.model.request.JobPosting;

import com.linkedin.sdk.lts.jobs.model.request.jobposting.JobPosting;
import com.linkedin.sdk.lts.jobs.model.request.jobposting.JobPostingOperationType;
import org.junit.Test;

import static com.linkedin.sdk.lts.jobs.constants.LinkedInCommonConstants.*;
import static org.junit.Assert.*;

public class JobPostingTest {

  private static final String COMPANY_ID = "12345";
  private static final String COMPANY_ID_NEW = "123456";
  private static final String EXTERNAL_JOB_POSTING_ID = "123";
  private static final String TITLE = "Software Engineer";
  private static final String DESCRIPTION = "Job description";

  @Test
  public void testIntegrationContextSetup() {
    JobPosting jobPosting = JobPosting.builder()
        .companyId(COMPANY_ID)
        .externalJobPostingId(EXTERNAL_JOB_POSTING_ID)
        .jobPostingOperationType(JobPostingOperationType.CREATE)
        .title(TITLE)
        .description(DESCRIPTION)
        .build();

    // Verify initial integrationContext
    assertEquals(jobPosting.getIntegrationContext(), LINKEDIN_URN_FORMAT + COMPANY_ID);

    // Update companyId and verify integrationContext
    jobPosting.setCompanyId(COMPANY_ID_NEW);
    assertEquals(jobPosting.getIntegrationContext(), LINKEDIN_URN_FORMAT + COMPANY_ID_NEW);

    jobPosting.setCompanyId(null);
    assertEquals(jobPosting.getIntegrationContext(), null);

    jobPosting.setCompanyId("");
    assertEquals(jobPosting.getIntegrationContext(), null);
  }
}
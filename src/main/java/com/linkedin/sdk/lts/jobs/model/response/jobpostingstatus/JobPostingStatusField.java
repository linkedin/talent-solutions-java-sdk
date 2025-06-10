package com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the status information for a LinkedIn job posting.
 * This class provides details about the status of a job posting as returned by the LinkedIn Talent Solutions API.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-args constructor</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/check-job-posting-status?view=li-lts-2025-04">LinkedIn Job Posting Status Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingStatusField {

  /**
   * Unique job posting id within the partner's system.
   * Required field.
   */
  private String externalJobPostingId;

  /**
   * Job posting information.
   * Optional field.
   */
  private JobPostingInfo jobPostingInfo;

  /**
   * Link to the job posting on LinkedIn.
   * Will only be present if job is listed on LinkedIn.
   * Optional field.
   */
  private String jobPostingUrl;

  /**
   * LinkedIn Apply status of the job posting.
   * Will be one of `ENABLED`, `NOT_ENABLED`, `IN_PROGRESS`.
   * Optional field.
   */
  private LinkedInApplyStatus linkedInApplyStatus;

  /**
   * Details about the LinkedIn Apply status.
   * Optional field.
   */
  private JobPostingStatusDetail linkedInApplyStatusDetail;

  /**
   * Listing state of the job posting.
   * Will be one of `LISTED`, `NOT_LISTED`, `IN_PROGRESS`.
   * Required field.
   */
  private ListingStatus listingStatus;

  /**
   * Error description for the job, if not listed successfully.
   * Optional field.
   */
  private JobPostingStatusDetail listingStatusDetail;

  /**
   * Promoted status of the job listing.
   * Will be one of `PROMOTED`, `NOT_PROMOTED`.
   * Optional field.
   */
  private PromotionStatus promotionStatus;

  /**
   * Instruction message to manage job promotion.
   * Optional field.
   */
  private JobPostingStatusDetail promotionStatusDetail;

  /**
   * Job campaign id (or `urn`) used for job promotion.
   * Will only be present if there is a corresponding LinkedIn job campaign for the specific job.
   * Will be in the format urn:li:jobCampaign:{linkedIn_jobCampaign_id}, for example, urn:li:jobCampaign:12844.
   * Optional field.
   */
  private String jobCampaign;

  /**
   * Partner job campaign id used for job promotion.
   * This value is either scraped from a source directly or sent in the job posting API payload by the partner.
   * May not always be numeric or map to a LinkedIn job campaign id (`jobCampaign`).
   * Optional field.
   */
  private String partnerJobCampaign;

  /**
   * The original location as inputted for this job.
   * Required field.
   */
  private String rawLocation;
}
package com.linkedin.sdk.lts.jobs.model.request.jobposting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.linkedin.sdk.lts.jobs.constants.LinkedInCommonConstants.*;


/**
 * Represents a basic job posting for LinkedIn's Job Posting API.
 * This class encapsulates all the necessary information for creating
 * and managing job postings on LinkedIn's platform.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/job-posting-api-schema?view=li-lts-2025-01">LinkedIn Job Posting Documentation</a>
 */
@Data
@NoArgsConstructor
public class JobPosting {

  /**
   * CompanyId for the job posting without the "urn:li:organization:" prefix
   * This field is used to set the integrationContext automatically.
   * This field is not present in the actual request schema.
   * This field will be ignored during serialization in favour of integrationContext.
   */
  @JsonIgnore
  private String companyId;

  /**
   * Client-specific integration context for the job posting.
   * Must be in the format "urn:li:organization:{company_id}".
   * This field is set automatically when companyId is set. Can't set manually.
   */
  @Setter(AccessLevel.NONE)
  private String integrationContext;

  /**
   * URL where candidates can apply for the job on the company's website.
   * Must be a valid URL and cannot be blank.
   */
  private String companyApplyUrl;

  /**
   * Client's unique identifier for the job posting.
   * Required field with maximum length of 75 characters.
   */
  private String externalJobPostingId;

  /**
   * Type of operation being performed on the job posting.
   * Required field with values from {@link JobPostingOperationType} enum:
   * CREATE, UPDATE, RENEW, CLOSE, UPGRADE, DOWNGRADE.
   */
  private JobPostingOperationType jobPostingOperationType;

  /**
   * Title of the job posting.
   * Required field with maximum length of 200 characters.
   */
  private String title;

  /**
   * Detailed description of the job posting.
   * Required field with length between 100 and 25,000 characters.
   * Should include responsibilities, requirements, and other relevant information.
   */
  private String description;

  /**
   * Unix timestamp (in milliseconds) when the job was listed.
   * Must be a positive epoch timestamp.
   */
  private Long listedAt;

  /**
   * Primary location where the job is based.
   * Required field that cannot be empty.
   * Should include city and country or region.
   */
  private String location;

  /**
   * Additional locations where the job could be based.
   * Optional list where each location must be non-empty if provided.
   */
  private List<String> alternateLocations;

  /**
   * Job categories that best describe the position.
   * Optional list with maximum of 3 categories.
   * Each category must be non-empty if provided.
   */
  private List<String> categories;

  /**
   * Description of required and desired skills for the position.
   * Optional field with maximum length of 4,000 characters.
   */
  private String skillsDescription;

  /**
   * Company's internal job code or reference number.
   */
  private String companyJobCode;

  /**
   * Types of workplace arrangements available for this position.
   * If provided, must contain exactly one value from {@link WorkplaceTypes} enum:
   * On-site, Hybrid, or Remote.
   */
  private List<WorkplaceTypes> workplaceTypes;

  /**
   * Industry categories relevant to the position.
   * Must be in the format "urn:li:industry:{industry_id}" if provided.
   */
  private List<String> industries;

  /**
   * Employment status for the position.
   * Optional value from {@link EmploymentStatus} enum:
   * FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, TEMPORARY, or VOLUNTEER.
   */
  private EmploymentStatus employmentStatus;

  /**
   * Required experience level for the position.
   * Optional value from {@link ExperienceLevel} enum:
   * ENTRY_LEVEL, MID_SENIOR_LEVEL, DIRECTOR, EXECUTIVE, INTERNSHIP, ASSOCIATE,
   * or NOT_APPLICABLE.
   */
  private ExperienceLevel experienceLevel;

  /**
   * URL for tracking pixel to monitor job posting performance.
   * Must be a valid URL if provided.
   */
  private String trackingPixelUrl;

  /**
   * Name of the company posting the job.
   * Optional field that must not be blank if provided.
   */
  private String companyName;

  /**
   * URL of the company's LinkedIn page or website.
   * Must be a valid URL if provided.
   */
  private String companyPageUrl;

  /**
   * Compensation information for the position.
   * If provided, must include valid compensations array with required fields:
   * - amount (optional): valid currency code and numeric amount
   * - value (required): range with start and end amounts
   * - period (required): from CompensationPeriod enum
   * - type (required): from CompensationType enum
   */
  private PosterProvidedCompensation compensation;

  /**
   * 	This date(Epoch in milliseconds) should be greater than the current timestamp + 24 hours.
   * 	If this field is not provided, the default expiration is 180 days for jobs.
   * 	For jobs, the expiration date must be within the range of 1 to 365 days,
   * 	for invalid date API will return an "Invalid Expiration Date" error.
   */
  private Long expireAt;

  /**
   * Represents the type of the job posting.
   * Value of PREMIUM should be provided only if integration is build for Premium Job Posting Extension schema.
   * For everything else, value of this field should be BASIC. Default value is BASIC.
   * This field is ignored when you use the jobPostingOperationType as UPGRADE or DOWNGRADE
   */
  private ListingType listingType;

  /**
   * Valid values are PUBLIC or INTERNAL. Every job must be either PUBLIC or INTERNAL
   */
  private Availability availability;

  /**
   * The email associated with the owner of the job posting.
   */
  private String posterEmail;

  /**
   * Unique ID of job requisition, used to link job posting and job requisition.
   * Required for RSC customers who sync requisitions via RSC integration. It should be the unique ID of the job
   * requisition for this job posting. A job requisition will be mapped to the ATS job requirement, Where all sourced
   * candidates and job applications are managed.	String	No. Yes for RSC customers
   */
  private String partnerRequisitionId;

  /**
   * Represents the contract this job posting is published to, which is signed by the customer
   * with LinkedIn to use LinkedIn Recruiter services. Must be in the format urn:li:contract:contractId
   */
  private String contract;

  /**
   * Represents whether LinkedIn would display the poster information on job description page.
   * The default value is false (No). For Basic jobs this only works if posterEmail is provided and
   * can be associated to a contract seat holder
   */
  private boolean showPosterInfo;

  /**
   * Sets the companyId and updates the integrationContext accordingly.
   */
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
    if (companyId != null && !companyId.isEmpty()) {
      this.integrationContext = LINKEDIN_URN_FORMAT + companyId;
    } else {
      this.integrationContext = null;
    }
  }

  /**
   * Constructs a JobPosting instance with the provided parameters.
   * This constructor is private to enforce the use of the Builder pattern.
   *
   * @param companyId The ID of the company posting the job.
   * @param companyApplyUrl The URL where candidates can apply for the job.
   * @param externalJobPostingId The client's unique identifier for the job posting.
   * @param jobPostingOperationType The type of operation being performed on the job posting.
   * @param title The title of the job posting.
   * @param description The detailed description of the job posting.
   * @param listedAt The timestamp when the job was listed.
   * @param location The primary location of the job.
   * @param alternateLocations Additional locations for the job.
   * @param categories Job categories relevant to the position.
   * @param skillsDescription Description of required and desired skills.
   * @param companyJobCode Company's internal job code or reference number.
   * @param workplaceTypes Types of workplace arrangements available for this position.
   * @param industries Industry categories relevant to the position.
   * @param employmentStatus Employment status for the position.
   * @param experienceLevel Required experience level for the position.
   * @param trackingPixelUrl URL for tracking pixel to monitor job posting performance.
   * @param companyName Name of the company posting the job.
   * @param companyPageUrl URL of the company's LinkedIn page or website.
   * @param compensation Compensation information for the position.
   * @param expireAt Expiration date for the job posting in epoch milliseconds.
   * @param listingType Type of job posting (BASIC or PREMIUM).
   * @param availability Availability status (PUBLIC or INTERNAL).
   * @param posterEmail Email associated with the owner of the job posting.
   * @param partnerRequisitionId Unique ID of job requisition linked to this posting.
   * @param contract Contract information for LinkedIn Recruiter services.
   * @param showPosterInfo Whether to display poster information on job description page.
   */
  @Builder
  private JobPosting(
      String companyId, String companyApplyUrl, String externalJobPostingId,
      JobPostingOperationType jobPostingOperationType, String title, String description,
      Long listedAt, String location, List<String> alternateLocations,
      List<String> categories, String skillsDescription, String companyJobCode,
      List<WorkplaceTypes> workplaceTypes, List<String> industries,
      EmploymentStatus employmentStatus, ExperienceLevel experienceLevel,
      String trackingPixelUrl, String companyName, String companyPageUrl,
      PosterProvidedCompensation compensation, Long expireAt,
      ListingType listingType, Availability availability,
      String posterEmail, String partnerRequisitionId,
      String contract, boolean showPosterInfo) {

    this.companyId = companyId;
    this.companyApplyUrl = companyApplyUrl;
    this.externalJobPostingId = externalJobPostingId;
    this.jobPostingOperationType = jobPostingOperationType;
    this.title = title;
    this.description = description;
    this.listedAt = listedAt;
    this.location = location;
    this.alternateLocations = alternateLocations;
    this.categories = categories;
    this.skillsDescription = skillsDescription;
    this.companyJobCode = companyJobCode;
    this.workplaceTypes = workplaceTypes;
    this.industries = industries;
    this.employmentStatus = employmentStatus;
    this.experienceLevel = experienceLevel;
    this.trackingPixelUrl = trackingPixelUrl;
    this.companyName = companyName;
    this.companyPageUrl = companyPageUrl;
    this.compensation = compensation;
    this.expireAt = expireAt;
    this.listingType = listingType;
    this.availability = availability;
    this.posterEmail = posterEmail;
    this.partnerRequisitionId = partnerRequisitionId;
    this.contract = contract;
    this.showPosterInfo = showPosterInfo;

    // Set integrationContext based on companyId
    if (companyId != null || !companyId.isEmpty()) {
      this.integrationContext = LINKEDIN_URN_FORMAT + companyId;
    }
  }

}

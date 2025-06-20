package com.linkedin.sdk.lts.jobs.model.request.applyconnect.applyConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents document upload question details with file size and type restrictions.
 * Used for documents like resumes (max 2MB) and cover letters (max 512KB).
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @Builder} - Implements the Builder pattern for object creation</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/onsite-apply-config-schema?view=li-lts-2025-04">LinkedIn Apply Connect Documentation</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentQuestionDetails {

  /**
   * Maximum allowed size of the attachment in bytes.
   * Resume: 2 MB max
   * Cover letter: 512 KB max
   * Default: 100 MB
   */
  private Long maximumSize;

  /**
   * Acceptable file extensions (e.g. doc, docx, pdf).
   * Must be in lowercase.
   * Null indicates no restrictions.
   * Empty array indicates only files with no extension are accepted.
   */
  private String[] acceptableFileExtensions;
}
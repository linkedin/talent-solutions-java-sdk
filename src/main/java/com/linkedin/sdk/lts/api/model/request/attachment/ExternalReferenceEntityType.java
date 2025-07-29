package com.linkedin.sdk.lts.api.model.request.attachment;

/**
 * Defines the kinds of external records an attachment can be linked to: Eg. either a job application or a candidate profile.
 *
 * <p>Supported reference entity types:</p>
 * <ul>
 *   <li>{@link #APPLICATION} – An attachment associated with a job application.</li>
 *   <li>{@link #CANDIDATE} – An attachment associated with a candidate profile.</li>
 * </ul>
 */
public enum ExternalReferenceEntityType {
  /**
   * Indicates the attachment belongs to a job application entity.
   */
  APPLICATION,

  /**
   * Indicates the attachment belongs to a candidate entity.
   */
  CANDIDATE
}

package com.linkedin.sdk.lts.api.model.request.attachment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents the composite key that uniquely identifies an attachment in the LinkedIn Talent Solutions system.
 * <p>
 * Ties together:
 * <ul>
 *   <li>The ID of the reference or associated entity to which the Attachment belongs (e.g., an application or candidate)</li>
 *   <li>The owning organization’s URN</li>
 *   <li>The identifier for the attachment in the external system</li>
 *   <li>The types of both the reference entity and the attachment itself</li>
 * </ul>
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Getter} – Generates getters for all fields.</li>
 *   <li>{@code @Builder} – Implements a fluent builder for object creation.</li>
 *   <li>{@code @ToString} – Generates a default {@code toString()} implementation.</li>
 *   <li>{@code @EqualsAndHashCode} – Generates {@code equals(...)} and {@code hashCode()} methods.</li>
 * </ul>
 */
@Builder
@Getter
@ToString
@EqualsAndHashCode
public final class AttachmentKey {
  /**
   * The unique ID assigned by the external system to the reference or associated entity
   * (for example, a job application or candidate) that this attachment belongs to.
   */
  @NonNull
  private final String externalReferenceEntityId;

  /**
   * The ID of the LinkedIn organization (company) that owns this attachment
   */
  @NonNull
  private final String organizationId;

  /**
   * The unique identifier assigned by the external system to this attachment.
   */
  @NonNull
  private final String externalAttachmentId;

  /**
   * Type of the external reference entity (APPLICATION or CANDIDATE).
   */
  @NonNull
  private final ExternalReferenceEntityType externalReferenceEntityType;

  /**
   * Type of the attachment (e.g., RESUME).
   */
  @NonNull
  private final AttachmentType attachmentType;
}

package com.linkedin.sdk.lts.api.model.request.attachment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents the metadata for an attachment uploaded to the LinkedIn Talent Solutions API.
 * <p>
 * Contains key attributes from the external system such as the original filename,
 * creation and modification timestamps, along with other contextual information required by the API.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Getter} – Generates getters for all fields.</li>
 *   <li>{@code @Builder} – Implements a fluent builder for object creation.</li>
 *   <li>{@code @ToString} – Generates a standard {@code toString()} implementation.</li>
 *   <li>{@code @EqualsAndHashCode} – Generates {@code equals(...)} and {@code hashCode()}.</li>
 * </ul>
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public final class AttachmentMetadata {
  /**
   * The filename as provided by the external system.
   */
  @NonNull
  private final String externalFileName;

  /**
   * Time at which the attachment was created within the partner system.
   * Number of milliseconds since midnight, January 1, 1970 UTC. It must be a positive number.
   */
  private final long externalCreatedAt;

  /**
   * Time at which the attachment was last modified within external system.
   * Number of milliseconds since midnight, January 1, 1970 UTC. It must be a positive number.
   */
  private final long externalLastModifiedAt;

  /**
   * Classification of the file (e.g., PDF, DOCX).
   */
  @NonNull
  private final FileType fileType;

  /**
   * Identifier for the contract or integration context under which
   * this attachment is managed.
   */
  @NonNull
  private final String contractId;
}

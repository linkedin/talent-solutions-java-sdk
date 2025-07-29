package com.linkedin.sdk.lts.api.model.request.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents a request to upload an attachment to the LinkedIn Talent Solutions API.
 * <p>
 * Encapsulates all information needed by the upload endpoint:
 * <ul>
 *   <li>{@link AttachmentKey} that uniquely identifies this attachment in the external system.</li>
 *   <li>{@link AttachmentContent} encapsulating actual content payload (file, bytes, or Base64 string).</li>
 *   <li>{@link AttachmentMetadata} describing the attachment (filename, timestamps, file type, contract context etc.).</li>
 * </ul>
 * <p>
 * The class uses Lombok annotations to generate boilerplate code:
 * <ul>
 *   <li>{@code @Getter} – Generates getters for all fields.</li>
 *   <li>{@code @Builder} – Provides a fluent builder for object creation.</li>
 *   <li>{@code @ToString} – Generates a default {@code toString()} implementation.</li>
 *   <li>{@code @EqualsAndHashCode} – Generates {@code equals(...)} and {@code hashCode()}.</li>
 *   <li>{@code @AllArgsConstructor} – Generates an all‑arguments constructor used by the builder.</li>
 * </ul>
 *
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AttachmentUploadRequest {

  /**
   * Identifies the attachment being uploaded, including the referenced entity,
   * owning organization, and attachment type.
   */
  @NonNull
  private final AttachmentKey key;

  /**
   * The content payload for this attachment. Exactly one of {@link AttachmentContent#getFile()},
   * {@link AttachmentContent#getBytes()} or {@link AttachmentContent#getBase64EncodedString()}
   * will be populated.
   */
  @NonNull
  private final AttachmentContent content;

  /**
   * Metadata for the attachment, such as the original filename, creation and
   * last‑modified timestamps, file type etc.
   */
  @NonNull
  private final AttachmentMetadata metadata;
}

package com.linkedin.sdk.lts.api.model.response.attachment;

import com.linkedin.sdk.lts.api.model.request.attachment.AttachmentKey;
import com.linkedin.sdk.lts.api.model.response.common.ApiError;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents a single-item result of an attachment upload operation in the LinkedIn Talent Solutions API.
 * <p>
 * This class corresponds to the per-item response structure defined for the attachment upload endpoint.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @ToString} – Generates a standard {@code toString()} implementation.</li>
 *   <li>{@code @Getter} – Generates getters for all fields.</li>
 *   <li>{@code @EqualsAndHashCode} – Generates {@code equals(...)} and {@code hashCode()}.</li>
 *   <li>{@code @Builder} – Implements the Builder pattern for object creation.</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/middleware-platform/sync-attachments?view=li-lts-2025-04">
 *      LinkedIn Attachment Upload Documentation</a>
 */
@ToString
@Getter
@EqualsAndHashCode
@Builder
public final class AttachmentUploadResponse {
  /**
   * The attachment key originally passed in the request.
   */
  @NonNull
  private final AttachmentKey key;

  /**
   * HTTP status code returned by the upload operation
   * (for example, 204 indicates success with no content).
   */
  private final int status;

  /**
   * Detailed error information when the upload failed.
   * Will be {@code null} if the upload succeeded.
   */
  private final ApiError error;

  /**
   * Convenience method that returns {@code true} if this response
   * indicates a failure (i.e. {@link #error} is non-null).
   *
   * @return {@code true} if an error occurred, {@code false} otherwise
   */
  public boolean isError() {
    return error != null;
  }
}

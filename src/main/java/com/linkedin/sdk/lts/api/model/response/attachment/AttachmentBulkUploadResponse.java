package com.linkedin.sdk.lts.api.model.response.attachment;

import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents an ordered batch result of bulk attachment upload operation in the LinkedIn Talent Solutions API.
 * <p>
 * Maintains the same order as the input requests and provides options to inspect successes and failures.
 *
 * <p>The class uses Lombok annotations to generate boilerplate code:</p>
 * <ul>
 *   <li>{@code @Getter} – Generates getters for all fields.</li>
 *   <li>{@code @ToString} – Generates a standard {@code toString()} implementation.</li>
 *   <li>{@code @EqualsAndHashCode} – Generates {@code equals(...)} and {@code hashCode()}.</li>
 * </ul>
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/middleware-platform/sync-attachments?view=li-lts-2025-04">
 *      LinkedIn Attachment Upload Documentation</a>
 */
@Getter
@ToString
@EqualsAndHashCode
public final class AttachmentBulkUploadResponse {

  /**
   * Immutable list of individual {@link AttachmentUploadResponse} results,
   * in the same order as the upload requests were issued.
   */
  @NonNull
  private final List<AttachmentUploadResponse> results;

  /**
   * Constructs a new {@code BulkUploadResponse} by making an unmodifiable copy
   * of the provided list of {@link AttachmentUploadResponse} objects.
   *
   * @param results the original list of upload responses (must not be null)
   * @throws NullPointerException if {@code results} is null
   */
  public AttachmentBulkUploadResponse(@NonNull List<AttachmentUploadResponse> results) {
    this.results = Collections.unmodifiableList(new ArrayList<>(results));
  }

  /**
   * Returns {@code true} if any of the upload responses in this batch
   * indicates a failure.
   *
   * @return {@code true} if at least one {@link AttachmentUploadResponse#isError() error} exists
   */
  public boolean hasFailures() {
    return results.stream().anyMatch(AttachmentUploadResponse::isError);
  }

  /**
   * Returns a list of all failed {@link AttachmentUploadResponse} items, in their
   * original input order. The returned list is unmodifiable.
   *
   * @return unmodifiable list of failed upload responses
   */
  public List<AttachmentUploadResponse> getFailures() {
    return Collections.unmodifiableList(results.stream().filter(AttachmentUploadResponse::isError).collect(Collectors.toList()));
  }

  /**
   * Returns a list of all successful {@link AttachmentUploadResponse} items, in their
   * original input order. The returned list is unmodifiable.
   *
   * @return unmodifiable list of successful upload responses
   */
  public List<AttachmentUploadResponse> getSuccesses() {
    return Collections.unmodifiableList(results.stream().filter(r -> !r.isError()).collect(Collectors.toList()));
  }
}

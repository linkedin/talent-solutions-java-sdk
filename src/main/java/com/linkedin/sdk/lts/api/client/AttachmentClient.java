package com.linkedin.sdk.lts.api.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.attachment.AttachmentUploadRequest;
import com.linkedin.sdk.lts.api.model.response.attachment.AttachmentBulkUploadResponse;
import com.linkedin.sdk.lts.api.model.response.attachment.AttachmentUploadResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * Public interface for the LinkedIn Talent Solutions Attachment API.
 * This client provides functionality to upload attachments to LinkedIn talent system through LinkedIn's Attachment Upload API.
 * Provides methods to upload single or batch attachments (e.g., resumes) on behalf of authenticated applications.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/talent/attachment/upload?view=li-lts-2025-04">
 *      LinkedIn Attachment Upload Documentation</a>
 */
public interface AttachmentClient {

  /**
   * Uploads a single attachment.
   *
   * @param attachmentUploadRequest the {@link AttachmentUploadRequest} containing:
   *        <ul>
   *          <li>{@link AttachmentUploadRequest#getKey()} – the unique key identifying this attachment</li>
   *          <li>{@link AttachmentUploadRequest#getContent()} – the payload (file, bytes, or Base64)</li>
   *          <li>{@link AttachmentUploadRequest#getMetadata()} – metadata (filename, timestamps, file type, contract)</li>
   *        </ul>
   *        Must not be null.
   * @return an {@link AttachmentUploadResponse} with:
   *         <ul>
   *           <li>{@link AttachmentUploadResponse#getKey()} ()} - the unique key identifying this attachment</li>
   *           <li>{@link AttachmentUploadResponse#getStatus()} – the HTTP status code</li>
   *           <li>{@link AttachmentUploadResponse#getError()} – non-null if upload failed</li>
   *           <li>{@link AttachmentUploadResponse#isError()} - true if upload failed</li>
   *         </ul>
   * @throws IllegalArgumentException if {@code requests} is null, empty, or contains invalid entries
   * @throws AuthenticationException  if authentication fails
   * @throws LinkedInApiException if the API returns an error response
   *  * <strong>Usage example:</strong>
   *  * <pre>{@code
   *  * AttachmentClient client = LinkedInClientFactory.INSTANCE.getAttachmentClient(clientId, clientSecret);
   *  *
   *  * UploadResponse single = client.upload(
   *  *     AttachmentUploadRequest.builder()
   *  *         .key(myAttachmentKey)
   *  *         .content(AttachmentContent.fromFile(myFile))
   *  *         .metadata(AttachmentMetadata.builder()
   *  *             .externalFileName("resume.pdf")
   *  *             .externalCreatedAt(1620000000000L)
   *  *             .externalLastModifiedAt(1620000000000L)
   *  *             .fileType(FileType.PDF)
   *  *             .contractId("contract123")
   *  *             .build())
   *  *         .build());
   *  *
   *  * if (single.isError()) {
   *  *   log.error("Upload failed for {}: {}", single.getKey(), single.getError());
   *  * } else {
   *  *   log.info("Upload succeeded with status {}", single.getStatus());
   *  * }
   *  * }</pre>
   *  *
   */
  AttachmentUploadResponse upload(AttachmentUploadRequest attachmentUploadRequest)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;

  /**
   * Uploads multiple attachments in a single batch operation. Batch size is limited to 10 at max.
   * <p>
   * The returned {@link AttachmentBulkUploadResponse} preserves the order of the input list.
   * If you are dealing with a large batch or prefer faster execution,
   * use the overloaded bulkUpload with option to pass ExecutorService for parallel execution.
   *
   * @param attachmentUploadRequests a non-null, non-empty {@link List} of {@link AttachmentUploadRequest}, elements must not be null.
   * @return a {@link AttachmentBulkUploadResponse} containing per-item {@link AttachmentUploadResponse}s
   * @throws IllegalArgumentException if {@code requests} is null, empty, or contains invalid entries
   * @throws AuthenticationException  if authentication fails
   * @throws LinkedInApiException if the API returns an error response
   * <strong>Usage example:</strong>
   * <pre>{@code
   * AttachmentClient client = LinkedInClientFactory.INSTANCE.getAttachmentClient(clientId, clientSecret);
   * UploadResponse request1 = AttachmentUploadRequest.builder()
   *         .key(myAttachmentKey)
   *         .content(AttachmentContent.fromFile(myFile))
   *         .metadata(AttachmentMetadata.builder()
   *             .externalFileName("resume.pdf")
   *             .externalCreatedAt(1620000000000L)
   *             .externalLastModifiedAt(1620000000000L)
   *             .fileType(FileType.PDF)
   *             .contractId("contract123")
   *             .build())
   *         .build();
   *
   * UploadResponse request2 = AttachmentUploadRequest.builder()
   *         .key(myAttachmentKey)
   *         .content(AttachmentContent.fromBytes(myByteArray))
   *         .metadata(AttachmentMetadata.builder()
   *             .externalFileName("resume2.pdf")
   *             .externalCreatedAt(1620000000000L)
   *             .externalLastModifiedAt(1620000000000L)
   *             .fileType(FileType.DOC)
   *             .contractId("contract123")
   *             .build())
   *         .build();
   *
   * // Batch upload
   * List<AttachmentUploadRequest> batch = Arrays.asList(request1, request2);
   * BulkUploadResponse batchResult = client.bulkUpload(batch);
   * if (batchResult.hasFailures()) {
   *   batchResult.getFailures().forEach(f ->
   *       log.error("Failed upload {}: {}", f.getKey(), f.getError()));
   * }
   * }</pre>
   *
   */
  AttachmentBulkUploadResponse bulkUpload(List<AttachmentUploadRequest> attachmentUploadRequests)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;

  /**
   * Uploads multiple attachments in parallel using the provided {@link ExecutorService}. Batch size is limited to 10 at max.
   * <p>
   * This overload lets you control concurrency by providing an ExecutorService with intended thread pool size
   * for large batches. The returned {@link AttachmentBulkUploadResponse} still preserves
   * the original request order in its success/failure lists.
   * This method will be faster than the vanilla bulkUpload method without the ExecutorService input.
   *
   * @param attachmentUploadRequests a non-null, non-empty list of {@link AttachmentUploadRequest};
   *                        elements must not be null
   * @param executorService the {@link ExecutorService} to use for concurrent uploads must not be null or shut down.
   * @return a {@link AttachmentBulkUploadResponse} containing per-item {@link AttachmentUploadResponse}s
   * @throws IllegalArgumentException if {@code requests} is null, empty, or contains invalid entries
   * @throws AuthenticationException  if authentication fails
   * @throws LinkedInApiException if the API returns an error response
   * <strong>Usage example:</strong>
   * <pre>{@code
   * AttachmentClient client = LinkedInClientFactory.INSTANCE.getAttachmentClient(clientId, clientSecret);
   *
   * UploadResponse request1 = AttachmentUploadRequest.builder()
   *         .key(myAttachmentKey)
   *         .content(AttachmentContent.fromFile(myFile))
   *         .metadata(AttachmentMetadata.builder()
   *             .externalFileName("resume.pdf")
   *             .externalCreatedAt(1620000000000L)
   *             .externalLastModifiedAt(1620000000000L)
   *             .fileType(FileType.PDF)
   *             .contractId("contract123")
   *             .build())
   *         .build();
   *
   * UploadResponse request2 = AttachmentUploadRequest.builder()
   *         .key(myAttachmentKey)
   *         .content(AttachmentContent.fromBytes(myByteArray))
   *         .metadata(AttachmentMetadata.builder()
   *             .externalFileName("resume2.pdf")
   *             .externalCreatedAt(1620000000000L)
   *             .externalLastModifiedAt(1620000000000L)
   *             .fileType(FileType.DOC)
   *             .contractId("contract123")
   *             .build())
   *         .build();
   *
   * // Batch upload
   * List<AttachmentUploadRequest> batch = Arrays.asList(request1, request2);
   * ExecutorService executor = Executors.newFixedThreadPool(5);
   * BulkUploadResponse batchResult = client.bulkUpload(batch, executor);
   * if (batchResult.hasFailures()) {
   *   batchResult.getFailures().forEach(f ->
   *       log.error("Failed upload {}: {}", f.getKey(), f.getError()));
   * }
   * }</pre>
   */
  AttachmentBulkUploadResponse bulkUpload(List<AttachmentUploadRequest> attachmentUploadRequests, ExecutorService executorService)
      throws AuthenticationException, LinkedInApiException, IllegalArgumentException;
}


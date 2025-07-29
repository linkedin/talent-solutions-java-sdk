package com.linkedin.sdk.lts.api.model.request.attachment;

import java.io.File;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


/**
 * Represents the content payload for an attachment to be uploaded using the LinkedIn Talent Solutions API.
 * <p>
 * Exactly one of the following sources will be non-null:
 * <ul>
 *   <li>{@link #getFile()} – a {@link File} to upload</li>
 *   <li>{@link #getBytes()} – a raw byte array payload</li>
 *   <li>{@link #getBase64EncodedString()} – a Base64‑encoded string payload</li>
 * </ul>
 * <p>
 * Use one of the static factory methods to create an instance. This class
 * makes defensive copies of mutable inputs and exposes immutable state.
 */
@ToString
@EqualsAndHashCode
public final class AttachmentContent {
  /**
   * Local file to be uploaded. Mutually exclusive with {@link #bytes} and {@link #base64EncodedString}.
   */
  @Getter
  private final File file;

  /**
   * Raw byte array payload. Cloned on input and output.
   * Mutually exclusive with {@link #file} and {@link #base64EncodedString}.
   */
  private final byte[] bytes;

  /**
   * Base64‑encoded string representation of the payload.
   * Mutually exclusive with {@link #file} and {@link #bytes}.
   */
  @Getter
  private final String base64EncodedString;

  // Private: only our factories can call this
  private AttachmentContent(File file, byte[] bytes, String base64EncodedString) {
    this.file = file;
    this.bytes = bytes;
    this.base64EncodedString = base64EncodedString;
  }

  /**
   * Creates content from a {@link File}.
   *
   * @param file the file to upload; must be non-null
   * @return an AttachmentContent wrapping the given file
   * @throws NullPointerException if {@code file} is null
   */
  public static AttachmentContent fromFile(@NonNull File file) {
    return new AttachmentContent(file, null, null);
  }

  /**
   * Creates content from a raw byte array.
   *
   * @param bytes the byte array to upload, must be non-null
   * @return an AttachmentContent wrapping a clone of the given bytes
   * @throws NullPointerException if {@code bytes} is null
   */
  public static AttachmentContent fromBytes(@NonNull byte[] bytes) {
    return new AttachmentContent(null, bytes.clone(), null);
  }

  /**
   * Creates content from a Base64‑encoded string.
   *
   * @param base64EncodedString the base64 string, must be non-null
   * @return an AttachmentContent wrapping the given base64 encoded string
   * @throws NullPointerException if {@code base64EncodedString} is null
   */
  public static AttachmentContent fromBase64EncodedString(@NonNull String base64EncodedString) {
    return new AttachmentContent(null, null, base64EncodedString);
  }

  /**
   * Returns a defensive copy of the raw byte payload, or null if this instance
   * was created from a file or Base64 string.
   *
   * @return cloned byte array or null
   */
  public byte[] getBytes() {
    return bytes == null ? null : bytes.clone();
  }
}

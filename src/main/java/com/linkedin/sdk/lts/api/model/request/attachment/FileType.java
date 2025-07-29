package com.linkedin.sdk.lts.api.model.request.attachment;

import lombok.NonNull;


/**
 * Enumeration of supported attachment file types in the LinkedIn Talent Solutions API.
 * <p>
 * Use {@link #fromString(String)} to safely parse an input string (case‑insensitive)
 * into a {@code FileType}, defaulting to {@link #UNKNOWN} if the value is unrecognized.
 */
public enum FileType {

  /**
   * Unknown or unspecified file type.
   */
  UNKNOWN,

  /**
   * Plain text file (.txt).
   */
  TXT,

  /**
   * Microsoft Word (binary) document (.doc).
   */
  DOC,

  /**
   * Microsoft Word document (.docx).
   */
  DOCX,

  /**
   * PDF document (.pdf)
   */
  PDF;

  /**
   * Parses the given string into a {@link FileType}, ignoring case and leading/trailing whitespace.
   * <p>
   * Returns {@link #UNKNOWN} if the trimmed value does not match any enum constant.
   *
   * @param name the file type name to parse (e.g. "pdf", "docx"); must not be null
   * @return the corresponding {@code FileType}, or {@code UNKNOWN} if no match
   * @throws NullPointerException if {@code name} is null
   */
  public static FileType fromString(@NonNull String name) {
    try {
      return FileType.valueOf(name.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      return UNKNOWN;
    }
  }
}
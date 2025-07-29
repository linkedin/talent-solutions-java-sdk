package com.linkedin.sdk.lts.api.model.response.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;


/**
 * Represents an error response from the LinkedIn Talent Solutions API.
 */
@Getter
public final class ApiError {
  /**
   * List of human‑readable error messages. Exposed via an unmodifiable view.
   */
  @NonNull
  private final List<String> messages;

  /**
   * Creates a new ApiError.
   *
   * @param messages initial error messages list (must not be null)
   * @throws NullPointerException if {@code messages} is null
   */
  public ApiError(@NonNull List<String> messages) {
    this.messages = new ArrayList<>(messages);
  }

  /**
   * Appends another error message to the messages list.
   *
   * @param message the message to add (must not be null)
   * @throws NullPointerException if {@code message} is null
   */
  public void addMessage(@NonNull String message) {
    this.messages.add(message);
  }

  /**
   * Returns an unmodifiable view of the error messages.
   * Any attempt to modify the returned list will throw {@link UnsupportedOperationException}.
   *
   * @return unmodifiable list of messages
   */
  public List<String> getMessages() {
    return Collections.unmodifiableList(messages);
  }
}
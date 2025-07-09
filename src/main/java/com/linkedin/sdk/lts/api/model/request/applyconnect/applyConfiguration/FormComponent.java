package com.linkedin.sdk.lts.api.model.request.applyconnect.applyConfiguration;

/**
 * Enum representing different form input component types.
 * Used to specify how a question should be presented in the UI.
 *
 * @See <a href="https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/onsite-apply-config-schema?view=li-lts-2025-04">LinkedIn Apply Connect Documentation</a>
 */
public enum FormComponent {
  /**
   * A series of checkboxes - one for each option.
   */
  CHECKBOXES,

  /**
   * A vertical list of options.
   */
  DROPDOWN,

  /**
   * A free-form text area that accepts multiple lines of text.
   */
  MULTILINE_TEXT,

  /**
   * A list of options with a toggle next to each one.
   * Selecting one option will deselect all others.
   */
  RADIO_BUTTONS,

  /**
   * A free-form text area that accepts only one line of text.
   */
  TEXT,

  /**
   * A series of buttons - one for each option.
   */
  BUTTONS
}
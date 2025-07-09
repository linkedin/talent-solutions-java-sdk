package com.linkedin.sdk.lts.internal.util;

import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

/**
 * LogRedactor is a utility class that redacts sensitive information from log messages.
 * It uses regular expressions to find and replace sensitive data such as access tokens
 * and client secrets with a redacted placeholder.
 */
public class LogRedactor {
  private static final Map<Pattern, String> PATTERNS = new HashMap<>();
  private static final String REDACTED = "****";

  static {
    // JSON patterns
    addPattern("\"access_token\"\\s*:\\s*\"[^\"]*\"", "\"access_token\":\"" + REDACTED + "\"");
    addPattern("\"client_secret\"\\s*:\\s*\"[^\"]*\"", "\"client_secret\":\"" + REDACTED + "\"");

    // Header patterns
    addPattern("Bearer\\s+[\\w\\-._~+/]+=*", "Bearer " + REDACTED);
    addPattern("Basic\\s+[\\w\\-._~+/]+=*", "Basic " + REDACTED);

    // URL patterns
    addPattern("client_secret=[^&]*", "client_secret=" + REDACTED);
    addPattern("access_token=[^&]*", "access_token=" + REDACTED);
  }

  private static void addPattern(String regex, String replacement) {
    PATTERNS.put(Pattern.compile(regex), replacement);
  }

  public static String redact(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }

    String result = input;
    for (Map.Entry<Pattern, String> entry : PATTERNS.entrySet()) {
      result = entry.getKey().matcher(result).replaceAll(entry.getValue());
    }
    return result;
  }
}

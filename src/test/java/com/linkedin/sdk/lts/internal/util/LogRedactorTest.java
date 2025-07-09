package com.linkedin.sdk.lts.util;

import com.linkedin.sdk.lts.internal.util.LogRedactor;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogRedactorTest {

  @Test
  public void testRedactJsonFields() {
    String input = "{"
        + "\"access_token\": \"secret-token-123\","
        + "\"client_secret\": \"very-secret-456\","
        + "\"safe_field\": \"not-redacted\""
        + "}";

    String redacted = LogRedactor.redact(input);

    // Normalize by removing spaces between JSON elements
    assertTrue(redacted.replaceAll("\\s+", "").contains("\"access_token\":\"****\""));
    assertTrue(redacted.replaceAll("\\s+", "").contains("\"client_secret\":\"****\""));
    assertTrue(redacted.replaceAll("\\s+", "").contains("\"safe_field\":\"not-redacted\""));
  }

  @Test
  public void testRedactAuthHeaders() {
    String bearerHeader = "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    String basicHeader = "Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    assertEquals("Authorization: Bearer ****", LogRedactor.redact(bearerHeader));
    assertEquals("Authorization: Basic ****", LogRedactor.redact(basicHeader));
  }

  @Test
  public void testRedactUrlParameters() {
    String url = "https://api.example.com/oauth2/token?client_id=123&client_secret=secret&access_token=token123";
    String redacted = LogRedactor.redact(url);

    assertTrue(redacted.contains("client_secret=****"));
    assertTrue(redacted.contains("access_token=****"));
    assertTrue(redacted.contains("client_id=123"));
  }

  @Test
  public void testHandleNullAndEmptyInput() {
    assertNull(LogRedactor.redact(null));
    assertEquals("", LogRedactor.redact(""));
  }

  @Test
  public void testPreserveNonSensitiveData() {
    String input = "{"
        + "\"id\": \"12345\","
        + "\"name\": \"John Doe\","
        + "\"email\": \"john@example.com\""
        + "}";

    String redacted = LogRedactor.redact(input);
    assertEquals(input, redacted);
  }
}
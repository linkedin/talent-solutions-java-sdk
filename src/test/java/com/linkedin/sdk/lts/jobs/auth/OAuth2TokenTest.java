package com.linkedin.sdk.lts.jobs.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class OAuth2TokenTest {

  private static final String TEST_TOKEN = "test-access-token";
  private static final long EXPIRY_SECONDS = 3600; // 1 hour

  @Test
  public void testIsExpired_WithValidToken_ReturnsFalse() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When/Then
    assertFalse("New token with future expiry should not be expired", token.isExpired());
  }

  @Test
  public void testIsExpired_WithExpiredToken_ReturnsTrue() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, -10); // Negative expiry = already expired

    // When/Then
    assertTrue("Token with past expiry should be expired", token.isExpired());
  }

  @Test
  public void testGetAccessToken_ReturnsCorrectToken() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When
    String accessToken = token.getAccessToken();

    // Then
    assertEquals("getAccessToken should return the token value", accessToken, TEST_TOKEN);
  }
}

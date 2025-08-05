package com.linkedin.sdk.lts.internal.auth;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class OAuth2TokenTest {

  @Test
  public void testTokenIsExpiredWithValidTokenShouldReturnsFalse() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When/Then
    assertFalse(token.isExpired(), "New token with future expiry should not be expired");
  }

  @Test
  public void testTokenIsExpiredWithExpiredTokenShouldReturnsTrue() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, -10); // Negative expiry = already expired

    // When/Then
    assertTrue(token.isExpired(), "Token with past expiry should be expired");
  }

  @Test
  public void testGetAccessTokenShouldReturnsCorrectToken() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When
    String accessToken = token.getAccessToken();

    // Then
    assertEquals(accessToken, TEST_TOKEN, "getAccessToken should return the token value");
  }
}

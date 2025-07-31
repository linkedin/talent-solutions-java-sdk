package com.linkedin.sdk.lts.internal.auth;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class OAuth2TokenTest {

  @Test
  public void testTokenIsExpiredWithValidTokenShouldReturnsFalse() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When/Then
    assertFalse("New token with future expiry should not be expired", token.isExpired());
  }

  @Test
  public void testTokenIsExpiredWithExpiredTokenShouldReturnsTrue() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, -10); // Negative expiry = already expired

    // When/Then
    assertTrue("Token with past expiry should be expired", token.isExpired());
  }

  @Test
  public void testGetAccessTokenShouldReturnsCorrectToken() {
    // Given
    OAuth2Token token = new OAuth2Token(TEST_TOKEN, EXPIRY_SECONDS);

    // When
    String accessToken = token.getAccessToken();

    // Then
    assertEquals("getAccessToken should return the token value", accessToken, TEST_TOKEN);
  }
}

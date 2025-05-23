package com.linkedin.sdk.lts.jobs.auth;

import com.linkedin.sdk.lts.jobs.exception.AuthenticationException;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.mockito.Mockito;

import static com.linkedin.sdk.lts.jobs.constants.HttpConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OAuth2ProviderTest {

  private static final String TEST_CLIENT_ID = "test-client-id";
  private static final String TEST_CLIENT_NEW_ID = "test-new-client-id";
  private static final String TEST_CLIENT_ID_WITH_SPACE = "test new client id";
  private static final String TEST_CLIENT_SECRET = "test-client-secret";
  private static final String TEST_CLIENT_SECRET_WITH_SPECIAL_CHARS = "secret@!#$%^&()";
  private static final String TEST_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
  private static final String TEST_ACCESS_TOKEN = "test-access-token";
  private static final String TEST_ACCESS_NEW_TOKEN = "test-access-new-token";
  private static final long TEST_EXPIRES_IN = 3600;

  private HttpsURLConnection mockConnection;

  private OAuth2Config config;
  private OAuth2Config configNewId;
  private OAuth2Config configSpecialChars;
  private OAuth2Provider providerSpy;
  private OAuth2Provider specialCharProviderSpy;

  @Before
  public void setUp() throws Exception {
    config = OAuth2Config.builder()
        .clientId(TEST_CLIENT_ID)
        .clientSecret(TEST_CLIENT_SECRET)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    configNewId = OAuth2Config.builder()
        .clientId(TEST_CLIENT_NEW_ID)
        .clientSecret(TEST_CLIENT_SECRET)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    configSpecialChars = OAuth2Config.builder()
        .clientId(TEST_CLIENT_ID_WITH_SPACE)
        .clientSecret(TEST_CLIENT_SECRET_WITH_SPECIAL_CHARS)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    // Clear the instances cache before each test to ensure clean state
    clearInstancesCache();

    providerSpy = Mockito.spy(OAuth2Provider.getInstance(config));
    specialCharProviderSpy = Mockito.spy(OAuth2Provider.getInstance(configSpecialChars));
    mockConnection = mock(HttpsURLConnection.class);
    when(providerSpy.openConnection(new URL(TEST_TOKEN_URL))).thenReturn(mockConnection);
    when(specialCharProviderSpy.openConnection(new URL(TEST_TOKEN_URL))).thenReturn(mockConnection);
  }

  @Test
  public void testGetInstance_ReturnsSameInstanceForSameConfig() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(config);

    assertSame("Should return the same instance for the same config", provider1, provider2);
  }

  @Test
  public void testGetInstance_ReturnsDifferentInstancesForDifferentConfigs() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(configNewId);

    assertNotSame("Should return different instances for different configs", provider1, provider2);
  }

  @Test
  public void testGetAccessToken_PerformsAuthentication_WhenNoTokenExists() throws Exception {
    setupMockConnectionForSuccess(TEST_ACCESS_TOKEN, new ByteArrayOutputStream());
    String accessToken = providerSpy.getAccessToken();

    assertEquals("Should return the correct access token", TEST_ACCESS_TOKEN, accessToken);
    assertTrue("Should return true when valid token exists", providerSpy.isTokenValid());
    verify(mockConnection).setRequestMethod(POST);
    verify(mockConnection).setRequestProperty(CONTENT_TYPE, APPLICATION_FORM_URLENCODED);
    verify(mockConnection).setRequestProperty(ACCEPT, APPLICATION_JSON);
    verify(mockConnection).setDoOutput(true);
  }

  @Test
  public void testGetAccessToken_ReusesToken_WhenValidTokenExists() throws Exception {
    setupMockConnectionForSuccess(TEST_ACCESS_TOKEN, new ByteArrayOutputStream());
    String firstToken = providerSpy.getAccessToken();
    reset(mockConnection);
    String secondToken = providerSpy.getAccessToken();
    assertEquals("Should return the same token", firstToken, secondToken);

    // Verify no new connection methods were called
    verify(mockConnection, never()).setRequestMethod(any());
    verify(mockConnection, never()).connect();
  }

  @Test
  public void testGetAccessToken_RefreshesToken_WhenTokenExpired() throws Exception {
    setupMockConnectionForSuccess(TEST_ACCESS_TOKEN, new ByteArrayOutputStream());
    providerSpy.getAccessToken();
    setExpiredToken(providerSpy);
    boolean isValid = providerSpy.isTokenValid();
    assertFalse("Should return false when token is expired", isValid);
    reset(mockConnection);
    setupMockConnectionForSuccess(TEST_ACCESS_NEW_TOKEN, new ByteArrayOutputStream());
    String refreshedToken = providerSpy.getAccessToken();
    assertEquals("Should return the new token", TEST_ACCESS_NEW_TOKEN, refreshedToken);
    verify(mockConnection).setRequestMethod("POST");
  }

  @Test(expected = AuthenticationException.class)
  public void testAuthenticate_ThrowsException_WhenHttpErrorOccurs() throws Exception {
    setupMockConnectionForError();
    providerSpy.getAccessToken();
  }

  @Test(expected = AuthenticationException.class)
  public void testAuthenticate_ThrowsException_WhenIOExceptionOccurs() throws Exception {
    when(mockConnection.getOutputStream()).thenThrow(new IOException("Network error"));
    providerSpy.getAccessToken();
  }

  @Test
  public void testIsTokenValid_ReturnsFalse_WhenNoToken() {
    boolean isValid = providerSpy.isTokenValid();
    assertFalse("Should return false when no token exists", isValid);
  }

  @Test
  public void testConnectionCleanup_ClosesConnection() throws Exception {
    setupMockConnectionForSuccess(TEST_ACCESS_TOKEN, new ByteArrayOutputStream());
    providerSpy.getAccessToken();
    verify(mockConnection).disconnect();
  }

  @Test
  public void testURLEncoding_HandlesSpecialCharacters() throws Exception {
    ByteArrayOutputStream captureStream = new ByteArrayOutputStream();
    setupMockConnectionForSuccess(TEST_ACCESS_TOKEN, captureStream);
    specialCharProviderSpy.getAccessToken();
    String requestBody = captureStream.toString(StandardCharsets.UTF_8.name());

    assertTrue("Client ID should be properly URL encoded",
        requestBody.contains("test+new+client+id"));
    assertTrue("Client secret should be properly URL encoded",
        requestBody.contains("secret%40%21%23%24%25%5E%26%28%29"));
  }

  // Helper methods
  private void setupMockConnectionForSuccess(String token, ByteArrayOutputStream outputStream) throws IOException {
    when(mockConnection.getOutputStream()).thenReturn(outputStream);

    String successResponse = String.format(
        "{\"access_token\":\"%s\",\"expires_in\":%d}",
        token, TEST_EXPIRES_IN);

    InputStream inputStream = new ByteArrayInputStream(
        successResponse.getBytes(StandardCharsets.UTF_8));
    when(mockConnection.getInputStream()).thenReturn(inputStream);
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
  }


  private void setupMockConnectionForError() throws IOException {
    when(mockConnection.getOutputStream()).thenReturn(new ByteArrayOutputStream());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
  }

  private void setExpiredToken(OAuth2Provider provider) throws Exception {
    Field tokenField = OAuth2Provider.class.getDeclaredField("currentToken");
    tokenField.setAccessible(true);
    OAuth2Token expiredToken = new OAuth2Token(TEST_ACCESS_TOKEN, -10); // Already expired
    tokenField.set(provider, expiredToken);
  }

  private void clearInstancesCache() throws Exception {
    Field instancesField = OAuth2Provider.class.getDeclaredField("INSTANCES");
    instancesField.setAccessible(true);
    ((java.util.concurrent.ConcurrentHashMap<?, ?>) instancesField.get(null)).clear();
  }
}
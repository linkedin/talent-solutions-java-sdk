package com.linkedin.sdk.lts.internal.auth;

import com.linkedin.sdk.lts.internal.client.TestingResourceUtility;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OAuth2ProviderTest {

  private static final String TEST_CLIENT_ID = "test-client-id";
  private static final String TEST_CLIENT_NEW_ID = "test-new-client-id";
  private static final String TEST_CLIENT_ID_WITH_SPACE = "test new client id";
  private static final String TEST_CLIENT_SECRET = "test-client-secret";
  private static final String TEST_CLIENT_SECRET_WITH_SPECIAL_CHARS = "secret@!#$%^&()";
  private static final String TEST_TOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
  private static final String TEST_ACCESS_TOKEN = "test-access-token";
  private static final String TEST_ACCESS_NEW_TOKEN = "new-test-access-token";
  private static final long TEST_EXPIRES_IN = 3600;

  private OAuth2Config config;
  private OAuth2Config configNewId;
  private OAuth2Config configSpecialChars;
  private OAuth2Provider providerSpy;
  private OAuth2Provider specialCharProviderSpy;
  @Mock
  private HttpClient httpClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
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

    providerSpy = Mockito.spy(OAuth2Provider.getInstance(config, httpClient));
    specialCharProviderSpy = Mockito.spy(OAuth2Provider.getInstance(configSpecialChars, httpClient));
  }

  @Test
  public void testGetInstance_ReturnsSameInstanceForSameConfig() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config, httpClient);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(config, httpClient);

    assertSame("Should return the same instance for the same config", provider1, provider2);
  }

  @Test
  public void testGetInstance_ReturnsDifferentInstancesForDifferentConfigs() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config, httpClient);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(configNewId, httpClient);

    assertNotSame("Should return different instances for different configs", provider1, provider2);
  }

  @Test
  public void testGetAccessToken_PerformsAuthentication_WhenNoTokenExists() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String accessToken = providerSpy.getAccessToken();

    assertEquals("Should return the correct access token", TEST_ACCESS_TOKEN, accessToken);
    assertTrue("Should return true when valid token exists", providerSpy.isTokenValid());

  }

  @Test
  public void testGetAccessToken_ReusesToken_WhenValidTokenExists() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String firstToken = providerSpy.getAccessToken();
    doReturn(TestingResourceUtility.getNewTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String secondToken = providerSpy.getAccessToken();
    assertEquals("Should return the same token", firstToken, secondToken);
    assertEquals("Should return the correct access token", TEST_ACCESS_TOKEN, firstToken);
    assertEquals("Should return the correct access token", TEST_ACCESS_TOKEN, secondToken);
  }

  @Test
  public void testGetAccessToken_RefreshesToken_WhenTokenExpired() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
    setExpiredToken(providerSpy);
    boolean isValid = providerSpy.isTokenValid();
    assertFalse("Should return false when token is expired", isValid);
    doReturn(TestingResourceUtility.getNewTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String refreshedToken = providerSpy.getAccessToken();
    assertEquals("Should return the new token", TEST_ACCESS_NEW_TOKEN, refreshedToken);
  }

  @Test(expected = AuthenticationException.class)
  public void testAuthenticate_ThrowsException_WhenHttpErrorOccurs() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
  }

  @Test(expected = AuthenticationException.class)
  public void testAuthenticate_ThrowsException_WhenIOExceptionOccurs() throws Exception {
    doThrow(new IOException("Network error")).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
  }

  @Test
  public void testIsTokenValid_ReturnsFalse_WhenNoToken() {
    boolean isValid = providerSpy.isTokenValid();
    assertFalse("Should return false when no token exists", isValid);
  }

  @Test
  public void testURLEncoding_HandlesSpecialCharacters() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    specialCharProviderSpy.getAccessToken();
    Mockito.verify(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(),
        eq("grant_type=client_credentials&client_id=test+new+client+id&client_secret=secret%40%21%23%24%25%5E%26%28%29"));
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
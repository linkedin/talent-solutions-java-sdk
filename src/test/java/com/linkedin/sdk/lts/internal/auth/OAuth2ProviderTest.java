package com.linkedin.sdk.lts.internal.auth;

import com.linkedin.sdk.lts.internal.client.TestingResourceUtility;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;

import java.io.IOException;
import java.lang.reflect.Field;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

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

  @BeforeMethod
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
  public void testGetInstanceShouldReturnSameInstanceForSameConfig() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config, httpClient);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(config, httpClient);

    assertSame(provider1, provider2, "Should return the same instance for the same config");
  }

  @Test
  public void testGetInstanceShouldReturnsDifferentInstancesForDifferentConfigs() {
    OAuth2Provider provider1 = OAuth2Provider.getInstance(config, httpClient);
    OAuth2Provider provider2 = OAuth2Provider.getInstance(configNewId, httpClient);

    assertNotSame(provider1, provider2, "Should return different instances for different configs");
  }

  @Test
  public void testGetAccessTokenShouldPerformsAuthenticationWhenNoTokenExists() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String accessToken = providerSpy.getAccessToken();

    assertEquals(TEST_ACCESS_TOKEN, accessToken, "Should return the correct access token");
    assertTrue(providerSpy.isTokenValid(), "Should return true when valid token exists");

  }

  @Test
  public void testGetAccessTokenShouldReusesTokenWhenValidTokenExists() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String firstToken = providerSpy.getAccessToken();
    doReturn(TestingResourceUtility.getNewTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String secondToken = providerSpy.getAccessToken();
    assertEquals(firstToken, secondToken, "Should return the same token");
    assertEquals(TEST_ACCESS_TOKEN, firstToken, "Should return the correct access token");
    assertEquals(TEST_ACCESS_TOKEN, secondToken, "Should return the correct access token");
  }

  @Test
  public void testGetAccessTokenShouldRefreshesTokenWhenTokenExpired() throws Exception {
    doReturn(TestingResourceUtility.getTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
    setExpiredToken(providerSpy);
    boolean isValid = providerSpy.isTokenValid();
    assertFalse(isValid, "Should return false when token is expired");
    doReturn(TestingResourceUtility.getNewTokenSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    String refreshedToken = providerSpy.getAccessToken();
    assertEquals(TEST_ACCESS_NEW_TOKEN, refreshedToken, "Should return the new token");
  }

  @Test(expectedExceptions = AuthenticationException.class)
  public void testAuthenticateShouldThrowsExceptionWhenHttpErrorOccurs() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
  }

  @Test(expectedExceptions = AuthenticationException.class)
  public void testAuthenticateShouldThrowsExceptionWhenIOExceptionOccurs() throws Exception {
    doThrow(new IOException("Network error")).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    providerSpy.getAccessToken();
  }

  @Test
  public void testIsTokenValidShouldReturnsFalseWhenNoToken() {
    boolean isValid = providerSpy.isTokenValid();
    assertFalse(isValid, "Should return false when no token exists");
  }

  @Test
  public void testURLEncodingShouldHandlesSpecialCharacters() throws Exception {
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
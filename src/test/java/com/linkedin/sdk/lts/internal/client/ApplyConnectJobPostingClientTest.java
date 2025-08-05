package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.applyconnect.jobApplicationNotification.JobApplicationNotificationRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import java.io.IOException;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class ApplyConnectJobPostingClientTest {

  private OAuth2Config config;

  @Mock
  private JobApplicationNotificationRequest mockRequest;
  @Mock
  private ApplyConnectJobPostingClientImpl client;
  @Mock
  private HttpClient httpClient;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    config = OAuth2Config.builder()
        .clientId(TestingCommonConstants.TEST_CLIENT_ID)
        .clientSecret(TestingCommonConstants.TEST_CLIENT_SECRET)
        .tokenUrl(TestingCommonConstants.TEST_TOKEN_URL)
        .build();

    client = Mockito.spy(new ApplyConnectJobPostingClientImpl(config, httpClient));
    Mockito.doReturn(TestingCommonConstants.TEST_TOKEN).when(client).getAccessToken();
    mockRequest = new JobApplicationNotificationRequest(); // Stub or use builder depending on implementation
  }

  @Test
  public void testSyncJobApplicationNotificationReturnsSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessJobPostingResponse()).when(httpClient).executeRequest(anyString(), eq(
        HttpMethod.POST), anyMap(), anyString());
    client.syncJobApplicationNotification(mockRequest);
  }

  @Test(expectedExceptions = LinkedInApiException.class,
      expectedExceptionsMessageRegExp = TestingCommonConstants.HTTP_400_MESSAGE)
  public void testSyncJobApplicationNotificationReturns400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    client.syncJobApplicationNotification(mockRequest);
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testSyncJobApplicationNotificationWithJsonSerializationException() throws Exception {
    try (MockedStatic<ObjectMapperUtil> mockedStatic = mockStatic(ObjectMapperUtil.class)) {
      mockedStatic.when(() -> ObjectMapperUtil.toJson(any()))
          .thenThrow(new JsonSerializationException(JSON_SERIALIZATION_ERROR));
        client.syncJobApplicationNotification(mockRequest);
    }
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testSyncJobApplicationNotificationWithNetworkIOError() throws Exception {
    doThrow(new IOException(NETWORK_ERROR_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    client.syncJobApplicationNotification(mockRequest);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testSyncJobApplicationNotificationWithNullJobApplicationNotificationRequest()
      throws AuthenticationException, LinkedInApiException {
      client.syncJobApplicationNotification(null);
  }
}

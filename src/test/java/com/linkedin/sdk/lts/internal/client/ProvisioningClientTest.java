package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.provisioning.CreateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.GetApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ProvisioningClientTest {

  private OAuth2Config config;

  @Mock
  private CreateApplicationRequest mockCreateApplicationRequest;
  @Mock
  private GetApplicationRequest mockGetApplicationRequest;
  @Mock
  private UpdateApplicationRequest mockUpdateApplicationRequest;
  @Mock
  private ProvisioningClientImpl client;
  @Mock
  private HttpClient httpClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    config = OAuth2Config.builder()
        .clientId(TestingCommonConstants.TEST_CLIENT_ID)
        .clientSecret(TestingCommonConstants.TEST_CLIENT_SECRET)
        .tokenUrl(TestingCommonConstants.TEST_TOKEN_URL)
        .build();

    client = Mockito.spy(new ProvisioningClientImpl(config, httpClient));
    Mockito.doReturn(TestingCommonConstants.TEST_TOKEN).when(client).getAccessToken();
    doReturn("uniqueForeignId").when(mockGetApplicationRequest).getUniqueForeignId();
    doReturn("urn:li:developerApplication:234").when(mockUpdateApplicationRequest).getDeveloperApplicationUrn();
  }

  @Test
  public void testCreateApplication_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getCreateApplicationSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    CreateApplicationResponse response = client.createApplication(mockCreateApplicationRequest);

    assertNotNull(response);
    assertNotNull(response.getCredentials());
    assertNotNull(response.getCredentials().getClientId());
    assertNotNull(response.getCredentials().getClientSecret());
    assertNotNull(response.getKey());
  }

  @Test
  public void testCreateApplication_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    Exception exception = assertThrows(Exception.class, () -> {
      client.createApplication(mockCreateApplicationRequest);
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testCreateApplication_nullRequest() throws Exception {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.createApplication(null);
    });

    assertEquals("CreateApplicationRequest cannot be null", exception.getMessage());
  }

  @Test
  public void testGetApplication_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getApplicationSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    GetApplicationResponse response = client.getApplication(mockGetApplicationRequest);

    assertNotNull(response);
    assertEquals(response.getElements().size(), 1);
    assertNotNull(response.getElements().get(0).getUniqueForeignId());
    assertNotNull(response.getElements().get(0).getKey());
    assertNotNull(response.getElements().get(0).getCredentials());
    assertNotNull(response.getElements().get(0).getCredentials().getClientId());
    assertNull(response.getElements().get(0).getCredentials().getClientSecret());
  }

  @Test
  public void testGetApplication_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(Exception.class, () -> {
      client.getApplication(mockGetApplicationRequest);
    });
    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testGetApplication_nullRequest() throws Exception {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getApplication(null);
    });

    assertEquals("GetApplicationRequest cannot be null", exception.getMessage());
  }

  @Test
  public void testGetApplication_nullUniqueForeignId() throws Exception {
    doReturn(null).when(mockGetApplicationRequest).getUniqueForeignId();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getApplication(mockGetApplicationRequest);
    });

    assertEquals("UniqueForeignId cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testUpdateApplication_successfulResponse() throws Exception {
    doReturn(null).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    client.updateApplication(mockUpdateApplicationRequest);
  }

  @Test
  public void testUpdateApplication_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    Exception exception = assertThrows(Exception.class, () -> {
      client.updateApplication(mockUpdateApplicationRequest);
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testUpdateApplication_nullRequest() throws Exception {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.updateApplication(null);
    });

    assertEquals("CreateApplicationRequest cannot be null", exception.getMessage());
  }

  @Test
  public void testUpdateApplication_nullDeveloperApplicationUrn() throws Exception {
    doReturn(null).when(mockUpdateApplicationRequest).getDeveloperApplicationUrn();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.updateApplication(mockUpdateApplicationRequest);
    });

    assertEquals("DeveloperApplicationUrn cannot be null or empty", exception.getMessage());
  }
}

package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.provisioning.CreateApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.GetApplicationRequest;
import com.linkedin.sdk.lts.api.model.request.provisioning.UpdateApplicationRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;

import java.util.HashMap;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;


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

  @BeforeMethod
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
  public void testCreateApplicationWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getCreateApplicationSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    APIResponse<CreateApplicationResponse> response = client.createApplication(mockCreateApplicationRequest);

    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getCredentials());
    assertNotNull(response.getBody().getCredentials().getClientId());
    assertNotNull(response.getBody().getCredentials().getClientSecret());
    assertNotNull(response.getBody().getKey());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testCreateApplicationWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        new HashMap<>(), TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    client.createApplication(mockCreateApplicationRequest);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testCreateApplicationWithNullRequest() throws Exception {
    client.createApplication(null);
  }

  @Test
  public void testGetApplicationWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getApplicationSuccessResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    APIResponse<GetApplicationResponse> response = client.getApplication(mockGetApplicationRequest);

    assertNotNull(response);
    assertEquals(response.getBody().getElements().size(), 1);
    assertNotNull(response.getBody().getElements().get(0).getUniqueForeignId());
    assertNotNull(response.getBody().getElements().get(0).getKey());
    assertNotNull(response.getBody().getElements().get(0).getCredentials());
    assertNotNull(response.getBody().getElements().get(0).getCredentials().getClientId());
    assertNull(response.getBody().getElements().get(0).getCredentials().getClientSecret());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetApplicationWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        new HashMap<>(), TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    client.getApplication(mockGetApplicationRequest);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetApplicationWithNullRequest() throws Exception {
    client.getApplication(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetApplicationWithNullUniqueForeignId() throws Exception {
    doReturn(null).when(mockGetApplicationRequest).getUniqueForeignId();
    client.getApplication(mockGetApplicationRequest);
  }

  @Test
  public void testUpdateApplicationWithSuccessfulResponse() throws Exception {
    doReturn(null).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    client.updateApplication(mockUpdateApplicationRequest);
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testUpdateApplicationWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        new HashMap<>(), TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    client.updateApplication(mockUpdateApplicationRequest);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testUpdateApplicationWithNullRequest() throws Exception {
    client.updateApplication(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testUpdateApplicationWithNullDeveloperApplicationUrn() throws Exception {
    doReturn(null).when(mockUpdateApplicationRequest).getDeveloperApplicationUrn();
    client.updateApplication(mockUpdateApplicationRequest);
  }
}

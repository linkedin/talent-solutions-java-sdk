package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.jobposting.JobPostingRequest;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.ListingStatus;
import com.linkedin.sdk.lts.api.model.response.jobtaskstatus.JobTaskStatus;
import com.linkedin.sdk.lts.api.model.response.jobtaskstatus.JobTaskStatusResponse;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;


public class JobPostingClientTest {

  private JobPostingRequest mockRequest;
  private OAuth2Config config;

  @Mock
  private JobPostingClientImpl client;
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

    client = Mockito.spy(new JobPostingClientImpl(config, httpClient));
    Mockito.doReturn(TestingCommonConstants.TEST_TOKEN).when(client).getAccessToken();
    mockRequest = new JobPostingRequest(); // Stub or use builder depending on implementation
  }

  @Test
  public void testPostWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessJobPostingResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    JobPostingResponse response = client.processJobPosting(mockRequest);

    assertEquals(response.getElements().get(0).getStatus(), 202);
    assertEquals(response.getElements().get(0).getId(), TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2);
    assertNull(response.getElements().get(0).getJobPostingError());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testPostWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    client.processJobPosting(mockRequest);
  }

  @Test
  public void testGetTaskStatusSingleIdWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessTaskStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    JobTaskStatusResponse response = client.getTaskStatus(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1);
    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1).getStatus().toValue(), JobTaskStatus.SUCCEEDED.toValue());
    assertTrue(response.getErrors().isEmpty());
    assertTrue(response.getStatuses().isEmpty());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetTaskStatusSingleIdWithNullTaskId() throws AuthenticationException, LinkedInApiException {
    client.getJobPostingStatus((String)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetTaskStatusSingleIdWithEmptyTaskId() throws AuthenticationException, LinkedInApiException {
    client.getTaskStatus("");
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetTaskStatusSingleIdWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    client.getTaskStatus(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1);
  }

  @Test
  public void testGetTaskStatusMultipleIdsWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getMultipleSuccessTaskStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    List<String> taskIds = Arrays.asList(
        TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1, TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2);
    JobTaskStatusResponse response = client.getTaskStatus(taskIds);

    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1).getStatus().toString(), JobTaskStatus.SUCCEEDED.toString());
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2).getStatus().toString(), JobTaskStatus.IN_PROGRESS.toString());
    assertTrue(response.getErrors().isEmpty());
    assertTrue(response.getStatuses().isEmpty());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetTaskStatusMultipleIdsWithNullTaskIds() throws AuthenticationException, LinkedInApiException {
    client.getTaskStatus((List<String>) null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetTaskStatusMultipleIdsWithEmptyTaskIds() throws AuthenticationException, LinkedInApiException {
    client.getTaskStatus(Arrays.asList());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetTaskStatusMultipleIdsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    client.getTaskStatus(Arrays.asList(
          TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1, TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2));
  }

  @Test
  public void testGetJobPostingStatusSingleIdWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessJobPostingStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    JobPostingStatusResponse response = client.getJobPostingStatus(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1);

    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingStatusSingleIdWithNullJobId() throws AuthenticationException, LinkedInApiException {
    client.getJobPostingStatus((String)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingStatusSingleIdWithEmptyJobId() throws AuthenticationException, LinkedInApiException {
    client.getJobPostingStatus("");
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetJobPostingStatusSingleIdWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    client.getJobPostingStatus(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1);
  }

  @Test
  public void testGetJobPostingStatusMultipleIdsWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getMultipleSuccessJobPostingStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    List<String> jobIds = Arrays.asList(
        TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1, TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2);
    JobPostingStatusResponse response = client.getJobPostingStatus(jobIds);

    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2).getListingStatus().toValue(), ListingStatus.NOT_LISTED.toValue());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingStatusMultipleIdsWithNullJobIds() throws AuthenticationException, LinkedInApiException {
    client.getJobPostingStatus((List<String>) null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingStatusMultipleIdsWithEmptyJobIds() throws AuthenticationException, LinkedInApiException {
    client.getJobPostingStatus(Arrays.asList());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetJobPostingStatusMultipleIdsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    client.getJobPostingStatus(Arrays.asList(
          TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1, TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2));
  }
}

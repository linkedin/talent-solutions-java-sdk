package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.JobPostingClient;
import com.linkedin.sdk.lts.api.client.LinkedInClientFactory;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class JobPostingClientTest {

  private JobPostingRequest mockRequest;
  private OAuth2Config config;

  @Mock
  private JobPostingClientImpl client;
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

    client = Mockito.spy(new JobPostingClientImpl(config, httpClient));
    Mockito.doReturn(TestingCommonConstants.TEST_TOKEN).when(client).getAccessToken();
    mockRequest = new JobPostingRequest(); // Stub or use builder depending on implementation
  }

  @Test
  public void testPost_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessJobPostingResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    JobPostingResponse response = client.post(mockRequest);

    assertEquals(response.getElements().get(0).getStatus(), 202);
    Assert.assertEquals(response.getElements().get(0).getId(), TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2);
    assertNull(response.getElements().get(0).getJobPostingError());
  }

  @Test
  public void testSingletonBehavior() throws Exception {
    JobPostingClient client1 = LinkedInClientFactory.getInstance().getJobPostingClient(
        TestingCommonConstants.TEST_CLIENT_ID, TestingCommonConstants.TEST_CLIENT_SECRET);
    JobPostingClient client2 = LinkedInClientFactory.getInstance().getJobPostingClient(
        TestingCommonConstants.TEST_CLIENT_ID, TestingCommonConstants.TEST_CLIENT_SECRET);
    assertSame(client1, client2);
  }

  @Test
  public void testPost_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    Exception exception = assertThrows(Exception.class, () -> {
      client.post(mockRequest);
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testGetTaskStatus_singleId_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessTaskStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    JobTaskStatusResponse response = client.getTaskStatus(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1);
    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1).getStatus().toValue(), JobTaskStatus.SUCCEEDED.toValue());
    assertTrue(response.getErrors().isEmpty());
    assertTrue(response.getStatuses().isEmpty());
  }


  @Test
  public void testGetTaskStatus_singleId_nullTaskId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getTaskStatus((String) null);
    });

    assertEquals("Task ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_singleId_emptyTaskId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getTaskStatus("");
    });

    assertEquals("Task ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_singleId_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(Exception.class, () -> {
      client.getTaskStatus(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1);
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testGetTaskStatus_multipleIds_successfulResponse() throws Exception {
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

  @Test
  public void testGetTaskStatus_multipleIds_nullTaskIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getTaskStatus((List<String>) null);
    });

    assertEquals("Task IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_multipleIds_emptyTaskIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getTaskStatus(Arrays.asList());
    });

    assertEquals("Task IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_multipleIds_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(Exception.class, () -> {
      client.getTaskStatus(Arrays.asList(
          TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1, TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2));
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testGetJobPostingStatus_singleId_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessJobPostingStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    JobPostingStatusResponse response = client.getJobPostingStatus(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1);

    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
  }

  @Test
  public void testGetJobPostingStatus_singleId_nullJobId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getJobPostingStatus((String)null);
    });

    assertEquals("Job Posting ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_singleId_emptyJobId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getJobPostingStatus("");
    });

    assertEquals("Job Posting ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_singleId_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(Exception.class, () -> {
      client.getJobPostingStatus(TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1);
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getMultipleSuccessJobPostingStatusResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    List<String> jobIds = Arrays.asList(
        TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1, TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2);
    JobPostingStatusResponse response = client.getJobPostingStatus(jobIds);

    assertNotNull(response);
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
    assertEquals(response.getResults().get(TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_2).getListingStatus().toValue(), ListingStatus.NOT_LISTED.toValue());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_nullJobIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getJobPostingStatus((List<String>) null);
    });

    assertEquals("Job Posting IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_emptyJobIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getJobPostingStatus(Arrays.asList());
    });

    assertEquals("Job Posting IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_error400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,
        TestingCommonConstants.HTTP_400_MESSAGE, TestingCommonConstants.HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(Exception.class, () -> {
      client.getJobPostingStatus(Arrays.asList(
          TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_1, TestingCommonConstants.TEST_JOB_POSTING_TASK_ID_2));
    });

    assertTrue(exception.getMessage().contains(TestingCommonConstants.HTTP_400_MESSAGE));
  }
}

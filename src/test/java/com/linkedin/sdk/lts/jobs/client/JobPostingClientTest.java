package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.constants.HttpConstants;
import com.linkedin.sdk.lts.jobs.model.request.jobposting.JobPostingRequest;
import com.linkedin.sdk.lts.jobs.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.jobs.model.response.jobpostingstatus.ListingStatus;
import com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus.JobTaskStatus;
import com.linkedin.sdk.lts.jobs.model.response.jobtaskstatus.JobTaskStatusResponse;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;

import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class JobPostingClientTest {

  private JobPostingClient client;
  private JobPostingClient clientSpy;
  private JobPostingRequest mockRequest;
  private HttpsURLConnection mockConnection;
  private OAuth2Config config;

  private static final String HTTP_400_MESSAGE = "HTTP error 400";

  @Before
  public void setUp() throws Exception {
    config = OAuth2Config.builder()
        .clientId(TEST_CLIENT_ID)
        .clientSecret(TEST_CLIENT_SECRET)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    client = JobPostingClient.getInstance(config);
    clientSpy = spy(client);
    mockConnection = mock(HttpsURLConnection.class);
    doReturn("mock-access-token").when(clientSpy).getAccessToken();
    doReturn(mockConnection).when(clientSpy).openConnection(any(URL.class));
    mockRequest = new JobPostingRequest(); // Stub or use builder depending on implementation
  }

  @Test
  public void testPost_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setDoOutput(true);
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.POST);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    OutputStream mockOutput = new ByteArrayOutputStream();
    when(mockConnection.getOutputStream()).thenReturn(mockOutput);
    when(mockConnection.getResponseCode()).thenReturn(201);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessJobPostingResponse().getBytes()));

    JobPostingResponse response = clientSpy.post(mockRequest);

    assertEquals(response.getElements().get(0).getStatus(), 202);
    assertEquals(response.getElements().get(0).getId(), TEST_JOB_POSTING_TASK_ID_2);
    assertNull(response.getElements().get(0).getJobPostingError());
  }

  @Test
  public void testSingletonBehavior() throws Exception {
    JobPostingClient client1 = LinkedInClientFactory.getInstance().getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    JobPostingClient client2 = LinkedInClientFactory.getInstance().getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    assertSame(client1, client2);
  }

  @Test
  public void testPost_error400Response() throws Exception {
    doNothing().when(mockConnection).setDoOutput(true);
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.POST);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    OutputStream mockOutput = new ByteArrayOutputStream();
    when(mockConnection.getOutputStream()).thenReturn(mockOutput);
    ByteArrayInputStream errorInput = new ByteArrayInputStream(
        "The payloads for premium job batch creation should contain both contract and operationType for each element".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(Exception.class, () -> {
      clientSpy.post(mockRequest);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetTaskStatus_singleId_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessTaskStatusResponse().getBytes()));

    JobTaskStatusResponse response = clientSpy.getTaskStatus(TEST_JOB_POSTING_TASK_ID_1);

    assertNotNull(response);
    assertEquals(response.getResults().get(TEST_JOB_POSTING_TASK_ID_1).getStatus().toValue(), JobTaskStatus.SUCCEEDED.toValue());
    assertTrue(response.getErrors().isEmpty());
    assertTrue(response.getStatuses().isEmpty());
  }


  @Test
  public void testGetTaskStatus_singleId_nullTaskId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getTaskStatus((String) null);
    });

    assertEquals("Task ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_singleId_emptyTaskId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getTaskStatus("");
    });

    assertEquals("Task ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_singleId_error400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid task ID".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(Exception.class, () -> {
      clientSpy.getTaskStatus(TEST_EXTERNAL_JOB_POSTING_ID_1);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetTaskStatus_multipleIds_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getMultipleSuccessTaskStatusResponse().getBytes()));

    List<String> taskIds = Arrays.asList(TEST_JOB_POSTING_TASK_ID_1, TEST_JOB_POSTING_TASK_ID_2);
    JobTaskStatusResponse response = clientSpy.getTaskStatus(taskIds);

    assertNotNull(response);
    assertEquals(response.getResults().get(TEST_JOB_POSTING_TASK_ID_1).getStatus().toString(), JobTaskStatus.SUCCEEDED.toString());
    assertEquals(response.getResults().get(TEST_JOB_POSTING_TASK_ID_2).getStatus().toString(), JobTaskStatus.IN_PROGRESS.toString());
    assertTrue(response.getErrors().isEmpty());
    assertTrue(response.getStatuses().isEmpty());
  }

  @Test
  public void testGetTaskStatus_multipleIds_nullTaskIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getTaskStatus((List<String>) null);
    });

    assertEquals("Task IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_multipleIds_emptyTaskIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getTaskStatus(Arrays.asList());
    });

    assertEquals("Task IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetTaskStatus_multipleIds_error400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid task IDs".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(Exception.class, () -> {
      clientSpy.getTaskStatus(Arrays.asList(TEST_EXTERNAL_JOB_POSTING_ID_1, TEST_EXTERNAL_JOB_POSTING_ID_2));
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetJobPostingStatus_singleId_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessJobPostingStatusResponse().getBytes()));

    JobPostingStatusResponse response = clientSpy.getJobPostingStatus(TEST_EXTERNAL_JOB_POSTING_ID_1);
    assertNotNull(response);
    assertEquals(response.getResults().get(TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
  }

  @Test
  public void testGetJobPostingStatus_singleId_nullJobId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getJobPostingStatus((String) null);
    });

    assertEquals("Job Posting ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_singleId_emptyJobId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getJobPostingStatus("");
    });

    assertEquals("Job Posting ID cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_singleId_error400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid job posting ID".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(Exception.class, () -> {
      clientSpy.getJobPostingStatus(TEST_JOB_POSTING_TASK_ID_1);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getMultipleSuccessJobPostingStatusResponse().getBytes()));

    List<String> jobIds = Arrays.asList(TEST_EXTERNAL_JOB_POSTING_ID_1, TEST_EXTERNAL_JOB_POSTING_ID_2);
    JobPostingStatusResponse response = clientSpy.getJobPostingStatus(jobIds);
    assertNotNull(response);
    assertEquals(response.getResults().get(TEST_EXTERNAL_JOB_POSTING_ID_1).getListingStatus().toValue(), ListingStatus.LISTED.toValue());
    assertEquals(response.getResults().get(TEST_EXTERNAL_JOB_POSTING_ID_2).getListingStatus().toValue(), ListingStatus.NOT_LISTED.toValue());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_nullJobIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getJobPostingStatus((List<String>) null);
    });

    assertEquals("Job Posting IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_emptyJobIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getJobPostingStatus(Arrays.asList());
    });

    assertEquals("Job Posting IDs list cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetJobPostingStatus_multipleIds_error400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid job posting IDs".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(Exception.class, () -> {
      clientSpy.getJobPostingStatus(Arrays.asList(TEST_JOB_POSTING_TASK_ID_1, TEST_JOB_POSTING_TASK_ID_2));
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }
}

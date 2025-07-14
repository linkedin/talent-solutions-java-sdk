package com.linkedin.sdk.lts.internal.client.linkedinclient;

import com.linkedin.sdk.lts.internal.client.TestingCommonConstants;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.linkedin.sdk.lts.internal.constants.HttpConstants.*;
import static com.linkedin.sdk.lts.internal.constants.LinkedInApiConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LinkedInHttpClientTest {

  private LinkedInHttpClient httpClient;

  private HttpsURLConnection mockConnection;
  private static final String TEST_URL = "https://api.linkedin.com/v2/test";
  private static final String TEST_RESPONSE = "{\"status\":\"success\"}";

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    mockConnection = mock(HttpsURLConnection.class);
    httpClient = spy(new LinkedInHttpClient(RetryConfig.builder().build()));
  }

  @Test
  public void testExecuteRequest_GET_Success() throws Exception {
    // Arrange
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        TEST_RESPONSE.getBytes(StandardCharsets.UTF_8)
    );

    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(inputStream);
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), eq(HttpMethod.GET));

    // Act
    String response = httpClient.executeRequest(TEST_URL, HttpMethod.GET, headers, null);

    // Assert
    assertEquals(TEST_RESPONSE, response);
  }

  @Test
  public void testExecuteRequest_POST_WithBody_Success() throws Exception {
    // Arrange
    String requestBody = TEST_RESPONSE.toString();
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        TEST_RESPONSE.getBytes(StandardCharsets.UTF_8)
    );

    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(inputStream);
    when(mockConnection.getOutputStream()).thenReturn(outputStream);
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), eq(HttpMethod.POST));

    // Act
    String response = httpClient.executeRequest(TEST_URL, HttpMethod.POST, headers, requestBody);

    // Assert
    assertEquals(TEST_RESPONSE, response);
    assertEquals(requestBody, outputStream.toString(StandardCharsets.UTF_8.name()));
    verify(mockConnection).setRequestProperty(CONTENT_LENGTH,
        String.valueOf(requestBody.getBytes(StandardCharsets.UTF_8).length));
  }

  @Test(expected = LinkedInApiException.class)
  public void testExecuteRequest_Error_Response() throws Exception {
    // Arrange
    String errorResponse = "{\"error\":\"Bad Request\"}";
    ByteArrayInputStream errorStream = new ByteArrayInputStream(
        errorResponse.getBytes(StandardCharsets.UTF_8)
    );

    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorStream);
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), eq(HttpMethod.GET));

    // Act
    httpClient.executeRequest(TEST_URL, HttpMethod.GET, new HashMap<>(), null);
  }

  @Test
  public void testExecuteRequest_TransientException() throws Exception {
    // Arrange
    httpClient = spy(new LinkedInHttpClient(RetryConfig.builder().build()));
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), any(HttpMethod.class));

    // Setup mock to throw TransientLinkedInApiException
    when(mockConnection.getResponseCode()).thenReturn(500); // Server error to trigger retry
    when(mockConnection.getErrorStream()).thenReturn(
        new ByteArrayInputStream("Server Error".getBytes(StandardCharsets.UTF_8))
    );

    // Act & Assert
    try {
      httpClient.executeRequest(TEST_URL, HttpMethod.GET, new HashMap<>(), null);
      fail("Expected LinkedInApiException to be thrown");
    } catch (LinkedInApiException e) {
      // Verify createConnection was called 4 times (initial + 3 retries)
      verify(httpClient, times(4)).createConnection(any(URL.class), any(HttpMethod.class));
      assertEquals(500, e.getStatusCode());
    }
  }

  @Test
  public void testExecuteRequest_WithHeaders() throws Exception {
    // Arrange
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    headers.put(AUTHORIZATION, TestingCommonConstants.TEST_TOKEN);
    headers.put(LINKEDIN_VERSION, API_VERSION_2025_04);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        TEST_RESPONSE.getBytes(StandardCharsets.UTF_8)
    );

    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(inputStream);
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), eq(HttpMethod.GET));

    // Act
    httpClient.executeRequest(TEST_URL, HttpMethod.GET, headers, null);

    // Assert
    verify(mockConnection).setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
    verify(mockConnection).setRequestProperty(AUTHORIZATION, TestingCommonConstants.TEST_TOKEN);
    verify(mockConnection).setRequestProperty(LINKEDIN_VERSION, API_VERSION_2025_04);
  }

  @Test
  public void testExecuteRequest_EmptyResponse() throws Exception {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);

    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(inputStream);
    doReturn(mockConnection).when(httpClient).createConnection(any(URL.class), eq(HttpMethod.GET));

    // Act
    String response = httpClient.executeRequest(TEST_URL, HttpMethod.GET, new HashMap<>(), null);

    // Assert
    assertEquals("", response);
  }
}
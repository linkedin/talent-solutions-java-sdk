package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.constants.HttpConstants;
import com.linkedin.sdk.lts.jobs.exception.LinkedInApiException;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByDate;
import com.linkedin.sdk.lts.jobs.model.response.common.Date;
import com.linkedin.sdk.lts.jobs.model.response.common.DateRange;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByIds;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByIds;
import java.io.ByteArrayInputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.*;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_CLIENT_ID;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_CLIENT_SECRET;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_TOKEN_URL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class P4PJobPostingClientTest {

  private P4PJobPostingClient client;
  private P4PJobPostingClient clientSpy;

  private HttpsURLConnection mockConnection;
  private OAuth2Config config;
  private P4PJobReportsRequestByIds p4PJobReportsRequestByIds;
  private P4PJobReportsRequestByDate p4PJobReportsRequestByDate;
  private static final String HTTP_400_MESSAGE = "HTTP error 400";


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    config = OAuth2Config.builder()
        .clientId(TEST_CLIENT_ID)
        .clientSecret(TEST_CLIENT_SECRET)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    client = P4PJobPostingClient.getInstance(config);
    clientSpy = spy(client);
    mockConnection = mock(HttpsURLConnection.class);
    doReturn(TEST_TOKEN).when(clientSpy).getAccessToken();
    doReturn(mockConnection).when(clientSpy).openConnection(any(URL.class));

    p4PJobReportsRequestByIds = P4PJobReportsRequestByIds.builder()
        .ids(Arrays.asList(TEST_EXTERNAL_JOB_POSTING_ID_1))
        .dateRange(DateRange.builder()
            .start(Date.builder().day(10).month(5).year(2025).build())
            .end(Date.builder().day(12).month(5).year(2025).build())
            .build())
        .partnerContractId(TEST_PARTNER_CONTRACT_ID)
        .build();

    p4PJobReportsRequestByDate = P4PJobReportsRequestByDate.builder()
        .dateRange(DateRange.builder()
            .start(Date.builder().day(10).month(5).year(2025).build())
            .end(Date.builder().day(12).month(5).year(2025).build())
            .build())
        .partnerContractId(TEST_PARTNER_CONTRACT_ID)
        .build();
  }

  @Test
  public void testGetP4PPerformanceReportByIds_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByIdsResponse().getBytes()));

    P4PReportResponseByIds response = clientSpy.getP4PReportByIds(p4PJobReportsRequestByIds);

    assertNotNull(response);
    assertEquals(1, response.getResults().size());
    assertTrue(response.getResults().containsKey(TEST_EXTERNAL_JOB_POSTING_ID_1));
  }

  @Test
  public void testGetP4PPerformanceReportByIds_errror400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid Request".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      clientSpy.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByIds_IllegalArgumentException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getP4PReportByIds(null);
    });

    assertEquals("P4PJobReportsRequestByIds cannot be null", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIds_nullIds() {
    p4PJobReportsRequestByIds.setIds(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Ids cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIds_nullDateRange() {
    p4PJobReportsRequestByIds.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDate_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByDateResponse().getBytes()));

    P4PReportResponseByDate response = clientSpy.getP4PReportsByDate(p4PJobReportsRequestByDate);

    assertNotNull(response);
    assertEquals(13, response.getElements().size());
  }

  @Test
  public void testGetP4PPerformanceReportByDate_errror400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid Request".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      clientSpy.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByDate_nullRequest() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getP4PReportsByDate(null);
    });

    assertEquals("P4PJobReportsRequestByDate cannot be null", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDate_nullDateRange() {
    p4PJobReportsRequestByDate.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }

  @Test
  public void testGetP4PPartnerBudgetReport_successfulResponse() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    when(mockConnection.getResponseCode()).thenReturn(200);
    when(mockConnection.getInputStream()).thenReturn(
        new ByteArrayInputStream(TestingResourceUtility.getSuccessP4PPartnerBudgetReportResponse().getBytes()));

    P4PBudgetReportResponse response = clientSpy.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);

    assertNotNull(response);
    assertFalse(response.getPartnerBudgetDetails().isEmpty());
  }

  @Test
  public void testGetP4PPartnerBudgetReport_errror400Response() throws Exception {
    doNothing().when(mockConnection).setRequestMethod(HttpConstants.GET);
    doNothing().when(mockConnection).setRequestProperty(anyString(), anyString());
    doNothing().when(mockConnection).setDoOutput(false);
    ByteArrayInputStream errorInput = new ByteArrayInputStream("Invalid Request".getBytes());
    when(mockConnection.getResponseCode()).thenReturn(400);
    when(mockConnection.getErrorStream()).thenReturn(errorInput);

    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      clientSpy.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPartnerBudgetReport_nullPartnerContractId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      clientSpy.getPartnerBudgetReports(null);
    });

    assertEquals("Partner contract ID cannot be null", exception.getMessage());
  }

}
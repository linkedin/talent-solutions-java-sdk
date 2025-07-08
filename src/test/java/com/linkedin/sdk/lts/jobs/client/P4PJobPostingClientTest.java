package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import com.linkedin.sdk.lts.jobs.client.linkedinclient.HttpClient;
import com.linkedin.sdk.lts.jobs.client.linkedinclient.LinkedInHttpClient;
import com.linkedin.sdk.lts.jobs.constants.HttpConstants;
import com.linkedin.sdk.lts.jobs.exception.LinkedInApiException;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByDate;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PProvisionCustomerHiringContractsRequest;
import com.linkedin.sdk.lts.jobs.model.response.common.Date;
import com.linkedin.sdk.lts.jobs.model.response.common.DateRange;
import com.linkedin.sdk.lts.jobs.model.request.p4pjobposting.P4PJobReportsRequestByIds;
import com.linkedin.sdk.lts.jobs.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PProvisionCustomerHiringContractsResponse;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.jobs.model.response.p4pjobposting.P4PReportResponseByIds;
import java.io.ByteArrayInputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

import static com.linkedin.sdk.lts.jobs.client.JobPostingClientTest.*;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.*;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_CLIENT_ID;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_CLIENT_SECRET;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1;
import static com.linkedin.sdk.lts.jobs.client.TestingCommonConstants.TEST_TOKEN_URL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class P4PJobPostingClientTest {

  private OAuth2Config config;
  private P4PJobReportsRequestByIds p4PJobReportsRequestByIds;
  private P4PJobReportsRequestByDate p4PJobReportsRequestByDate;
  private P4PProvisionCustomerHiringContractsRequest p4PProvisionCustomerHiringContractsRequest;

  @Mock
  private P4PJobPostingClient client;
  @Mock
  private LinkedInHttpClient httpClient;


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    config = OAuth2Config.builder()
        .clientId(TEST_CLIENT_ID)
        .clientSecret(TEST_CLIENT_SECRET)
        .tokenUrl(TEST_TOKEN_URL)
        .build();

    client = Mockito.spy(new P4PJobPostingClient(config, httpClient));
    doReturn(TEST_TOKEN).when(client).getAccessToken();

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

    p4PProvisionCustomerHiringContractsRequest = P4PProvisionCustomerHiringContractsRequest.builder()
        .partnerContractId(TEST_PARTNER_CONTRACT_ID)
        .build();
  }

  @Test
  public void provisionCustomerHiringContracts_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4ProvisionCustomerHiringContractsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    P4PProvisionCustomerHiringContractsResponse response = client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);

    assertNotNull(response);
    assertNotNull(response.getValue().getContract());
    assertNotNull(response.getValue().getKey());
  }

  @Test
  public void provisionCustomerHiringContracts_errror400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void provisionCustomerHiringContracts_IllegalArgumentException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.provisionCustomerHiringContracts(null);
    });

    assertEquals("P4PProvisionCustomerHiringContractsRequest cannot be null", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIds_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByIdsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PReportResponseByIds response = client.getP4PReportByIds(p4PJobReportsRequestByIds);

    assertNotNull(response);
    assertEquals(1, response.getResults().size());
    assertTrue(response.getResults().containsKey(TEST_EXTERNAL_JOB_POSTING_ID_1));
  }

  @Test
  public void testGetP4PPerformanceReportByIds_errror400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByIds_IllegalArgumentException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(null);
    });

    assertEquals("P4PJobReportsRequestByIds cannot be null", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIds_nullIds() {
    p4PJobReportsRequestByIds.setIds(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Ids cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIds_nullDateRange() {
    p4PJobReportsRequestByIds.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDate_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByDateResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PReportResponseByDate response = client.getP4PReportsByDate(p4PJobReportsRequestByDate);

    assertNotNull(response);
    assertEquals(13, response.getElements().size());
  }

  @Test
  public void testGetP4PPerformanceReportByDate_errror400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByDate_nullRequest() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportsByDate(null);
    });

    assertEquals("P4PJobReportsRequestByDate cannot be null", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDate_nullDateRange() {
    p4PJobReportsRequestByDate.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }

  @Test
  public void testGetP4PPartnerBudgetReport_successfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PPartnerBudgetReportResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PBudgetReportResponse response = client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);

    assertNotNull(response);
    assertFalse(response.getPartnerBudgetDetails().isEmpty());
  }

  @Test
  public void testGetP4PPartnerBudgetReport_errror400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPartnerBudgetReport_nullPartnerContractId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getPartnerBudgetReports(null);
    });

    assertEquals("Partner contract ID cannot be null", exception.getMessage());
  }

}
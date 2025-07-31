package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.internal.auth.OAuth2Config;
import com.linkedin.sdk.lts.internal.client.linkedinclient.LinkedInHttpClient;
import com.linkedin.sdk.lts.api.exception.LinkedInApiException;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PJobReportsRequestByDate;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PProvisionCustomerHiringContractsRequest;
import com.linkedin.sdk.lts.api.model.response.common.Date;
import com.linkedin.sdk.lts.api.model.response.common.DateRange;
import com.linkedin.sdk.lts.api.model.request.p4pjobposting.P4PJobReportsRequestByIds;
import com.linkedin.sdk.lts.api.model.response.common.HttpMethod;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PProvisionCustomerHiringContractsResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByIds;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_CLIENT_ID;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_CLIENT_SECRET;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_TOKEN_URL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class P4PJobPostingClientTest {

  private OAuth2Config config;
  private P4PJobReportsRequestByIds p4PJobReportsRequestByIds;
  private P4PJobReportsRequestByDate p4PJobReportsRequestByDate;
  private P4PProvisionCustomerHiringContractsRequest p4PProvisionCustomerHiringContractsRequest;

  @Mock
  private P4PJobPostingClientImpl client;
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

    client = Mockito.spy(new P4PJobPostingClientImpl(config, httpClient));
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
  public void provisionCustomerHiringContractsWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4ProvisionCustomerHiringContractsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    P4PProvisionCustomerHiringContractsResponse response = client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);

    assertNotNull(response);
    assertNotNull(response.getValue().getContract());
    assertNotNull(response.getValue().getKey());
  }

  @Test
  public void provisionCustomerHiringContractsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void provisionCustomerHiringContractsWithNullP4PProvisionCustomerHiringContractsRequest() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.provisionCustomerHiringContracts(null);
    });

    assertEquals("P4PProvisionCustomerHiringContractsRequest cannot be null", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByIdsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PReportResponseByIds response = client.getP4PReportByIds(p4PJobReportsRequestByIds);

    assertNotNull(response);
    assertEquals(1, response.getResults().size());
    assertTrue(response.getResults().containsKey(TEST_EXTERNAL_JOB_POSTING_ID_1));
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWithNullP4PJobReportsRequestByIds() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(null);
    });

    assertEquals("P4PJobReportsRequestByIds cannot be null", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWithNullIds() {
    p4PJobReportsRequestByIds.setIds(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Ids cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWithNullDateRange() {
    p4PJobReportsRequestByIds.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportByIds(p4PJobReportsRequestByIds);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDateWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByDateResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PReportResponseByDate response = client.getP4PReportsByDate(p4PJobReportsRequestByDate);

    assertNotNull(response);
    assertEquals(13, response.getElements().size());
  }

  @Test
  public void testGetP4PPerformanceReportByDateWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPerformanceReportByDateWithNullP4PJobReportsRequestByDate() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportsByDate(null);
    });

    assertEquals("P4PJobReportsRequestByDate cannot be null", exception.getMessage());
  }


  @Test
  public void testGetP4PPerformanceReportByDateWithNullDateRange() {
    p4PJobReportsRequestByDate.setDateRange(null);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getP4PReportsByDate(p4PJobReportsRequestByDate);
    });

    assertEquals("Date range cannot be null and must have both start and end dates", exception.getMessage());
  }

  @Test
  public void testGetP4PPartnerBudgetReportWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PPartnerBudgetReportResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    P4PBudgetReportResponse response = client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);

    assertNotNull(response);
    assertFalse(response.getPartnerBudgetDetails().isEmpty());
  }

  @Test
  public void testGetP4PPartnerBudgetReportWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,HTTP_400_MESSAGE, HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull());
    Exception exception = assertThrows(LinkedInApiException.class, () -> {
      client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);
    });

    assertTrue(exception.getMessage().contains(HTTP_400_MESSAGE));
  }

  @Test
  public void testGetP4PPartnerBudgetReportWithNullPartnerContractId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      client.getPartnerBudgetReports(null);
    });

    assertEquals("Partner contract ID cannot be null", exception.getMessage());
  }

}
package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.exception.AuthenticationException;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
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

import java.util.HashMap;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;

import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.*;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_CLIENT_ID;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_CLIENT_SECRET;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_EXTERNAL_JOB_POSTING_ID_1;
import static com.linkedin.sdk.lts.internal.client.TestingCommonConstants.TEST_TOKEN_URL;
import static org.testng.Assert.*;
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


  @BeforeMethod
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
    doReturn(TestingResourceUtility.getSuccessP4ProvisionCustomerHiringContractsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    APIResponse<P4PProvisionCustomerHiringContractsResponse> response = client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);

    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getValue().getContract());
    assertNotNull(response.getBody().getValue().getKey());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void provisionCustomerHiringContractsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 , new HashMap<>(), HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.POST), anyMap(), anyString(), any());
    client.provisionCustomerHiringContracts(p4PProvisionCustomerHiringContractsRequest);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void provisionCustomerHiringContractsWithNullP4PProvisionCustomerHiringContractsRequest()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException, JsonSerializationException {
    client.provisionCustomerHiringContracts(null);
  }

  @Test
  public void testGetP4PPerformanceReportByIdsWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByIdsResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    APIResponse<P4PReportResponseByIds> response = client.getP4PReportByIds(p4PJobReportsRequestByIds);

    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().getResults().size());
    assertTrue(response.getBody().getResults().containsKey(TEST_EXTERNAL_JOB_POSTING_ID_1));
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetP4PPerformanceReportByIdsWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,new HashMap<>(), HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    client.getP4PReportByIds(p4PJobReportsRequestByIds);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPerformanceReportByIdsWithNullP4PJobReportsRequestByIds()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    client.getP4PReportByIds(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPerformanceReportByIdsWithNullIds()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    p4PJobReportsRequestByIds.setIds(null);
    client.getP4PReportByIds(p4PJobReportsRequestByIds);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPerformanceReportByIdsWithNullDateRange()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    p4PJobReportsRequestByIds.setDateRange(null);
    client.getP4PReportByIds(p4PJobReportsRequestByIds);
  }


  @Test
  public void testGetP4PPerformanceReportByDateWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PJobPerformanceReportsByDateResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    APIResponse<P4PReportResponseByDate> response = client.getP4PReportsByDate(p4PJobReportsRequestByDate);

    assertNotNull(response.getBody());
    assertEquals(13, response.getBody().getElements().size());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetP4PPerformanceReportByDateWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,new HashMap<>(), HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    client.getP4PReportsByDate(p4PJobReportsRequestByDate);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPerformanceReportByDateWithNullP4PJobReportsRequestByDate()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    client.getP4PReportsByDate(null);
  }


  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPerformanceReportByDateWithNullDateRange()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    p4PJobReportsRequestByDate.setDateRange(null);
    client.getP4PReportsByDate(p4PJobReportsRequestByDate);
  }

  @Test
  public void testGetP4PPartnerBudgetReportWithSuccessfulResponse() throws Exception {
    doReturn(TestingResourceUtility.getSuccessP4PPartnerBudgetReportResponse()).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    APIResponse<P4PBudgetReportResponse> response = client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);

    assertNotNull(response.getBody());
    assertFalse(response.getBody().getPartnerBudgetDetails().isEmpty());
  }

  @Test(expectedExceptions = LinkedInApiException.class)
  public void testGetP4PPartnerBudgetReportWith400Response() throws Exception {
    doThrow(new LinkedInApiException(400 ,new HashMap<>(), HTTP_400_MESSAGE)).when(httpClient).executeRequest(anyString(), eq(HttpMethod.GET), anyMap(), isNull(), any());
    client.getPartnerBudgetReports(TEST_PARTNER_CONTRACT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PPartnerBudgetReportWithNullPartnerContractId()
      throws AuthenticationException, LinkedInApiException, JsonDeserializationException {
    client.getPartnerBudgetReports(null);
  }
}
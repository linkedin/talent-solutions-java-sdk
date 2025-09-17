package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.JobPostingClient;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.model.response.common.APIResponse;
import com.linkedin.sdk.lts.api.model.response.jobposting.JobPostingResponse;
import com.linkedin.sdk.lts.api.model.response.jobpostingstatus.JobPostingStatusResponse;
import com.linkedin.sdk.lts.api.model.response.jobtaskstatus.JobTaskStatusResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PBudgetReportResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PProvisionCustomerHiringContractsResponse;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByDate;
import com.linkedin.sdk.lts.api.model.response.p4pjobposting.P4PReportResponseByIds;
import com.linkedin.sdk.lts.api.model.response.provisioning.CreateApplicationResponse;
import com.linkedin.sdk.lts.api.model.response.provisioning.GetApplicationResponse;
import com.linkedin.sdk.lts.internal.auth.TokenInfo;
import com.linkedin.sdk.lts.internal.util.ObjectMapperUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestingResourceUtility {

  public static APIResponse<JobPostingResponse> getSuccessJobPostingResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("JobPostingSuccessResponse.json");
    return APIResponse.<JobPostingResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, JobPostingResponse.class))
        .build();
  }

  public static APIResponse<JobTaskStatusResponse> getSuccessTaskStatusResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("TaskStatusSuccessResponse.json");
    return APIResponse.<JobTaskStatusResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, JobTaskStatusResponse.class))
        .build();
  }

  public static APIResponse<JobTaskStatusResponse> getMultipleSuccessTaskStatusResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("MultipleTaskStatusSuccessResponse.json");
    return APIResponse.<JobTaskStatusResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, JobTaskStatusResponse.class))
        .build();
  }

  public static APIResponse<JobPostingStatusResponse> getSuccessJobPostingStatusResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("JobPostingStatusSuccessResponse.json");
    return APIResponse.<JobPostingStatusResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, JobPostingStatusResponse.class))
        .build();
  }

  public static APIResponse<JobPostingStatusResponse> getMultipleSuccessJobPostingStatusResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("MultipleJobPostingStatusSuccessResponse.json");
    return APIResponse.<JobPostingStatusResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, JobPostingStatusResponse.class))
        .build();
  }

  public static APIResponse<P4PProvisionCustomerHiringContractsResponse> getSuccessP4ProvisionCustomerHiringContractsResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("P4PProvisionCustomerHiringContractsSuccessResponse.json");
    return APIResponse.<P4PProvisionCustomerHiringContractsResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, P4PProvisionCustomerHiringContractsResponse.class))
        .build();
  }

  public static APIResponse<P4PReportResponseByIds> getSuccessP4PJobPerformanceReportsByIdsResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("P4PJobPerformanceReportsByIdsSuccessResponse.json");
    return APIResponse.<P4PReportResponseByIds>builder()
        .body(ObjectMapperUtil.fromJson(json, P4PReportResponseByIds.class))
        .build();
  }

  public static APIResponse<P4PReportResponseByDate> getSuccessP4PJobPerformanceReportsByDateResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("P4PJobPerformanceReportsByDateSuccessResponse.json");
    return APIResponse.<P4PReportResponseByDate>builder()
        .body(ObjectMapperUtil.fromJson(json, P4PReportResponseByDate.class))
        .build();
  }

  public static APIResponse<P4PBudgetReportResponse> getSuccessP4PPartnerBudgetReportResponse()
      throws JsonDeserializationException {
    String json = readJsonFromFile("P4PPartnerBudgetReportResponse.json");
    return APIResponse.<P4PBudgetReportResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, P4PBudgetReportResponse.class))
        .build();
  }

  public static APIResponse<CreateApplicationResponse> getCreateApplicationSuccessResponse()
      throws JsonDeserializationException {
    String json = readJsonFromFile("CreateApplicationSuccessResponse.json");
    return APIResponse.<CreateApplicationResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, CreateApplicationResponse.class))
        .build();
  }

  public static APIResponse<GetApplicationResponse> getApplicationSuccessResponse()
      throws JsonDeserializationException {
    String json = readJsonFromFile("GetApplicationSuccessResponse.json");
    return APIResponse.<GetApplicationResponse>builder()
        .body(ObjectMapperUtil.fromJson(json, GetApplicationResponse.class))
        .build();
  }

  public static APIResponse<TokenInfo> getTokenSuccessResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("TokenSuccessResponse.json");
    return APIResponse.<TokenInfo>builder()
        .body(ObjectMapperUtil.fromJson(json, TokenInfo.class))
        .build();
  }

  public static APIResponse<TokenInfo> getNewTokenSuccessResponse() throws JsonDeserializationException {
    String json = readJsonFromFile("NewTokenSuccessResponse.json");
    return APIResponse.<TokenInfo>builder()
        .body(ObjectMapperUtil.fromJson(json, TokenInfo.class))
        .build();
  }

  public static String readJsonFromFile(String filePath) {
    InputStream inputStream = JobPostingClient.class.getClassLoader().getResourceAsStream(filePath);
    if (inputStream == null) {
      throw new IllegalArgumentException("File not found: " + filePath);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    } catch (Exception e) {
      throw new RuntimeException("Failed to read file: " + filePath, e);
    }
  }
}
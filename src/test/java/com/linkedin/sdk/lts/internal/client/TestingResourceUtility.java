package com.linkedin.sdk.lts.internal.client;

import com.linkedin.sdk.lts.api.client.JobPostingClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestingResourceUtility {

  public static String getSuccessJobPostingResponse(){
    return readJsonFromFile("JobPostingSuccessResponse.json");
  }

  public static String getSuccessTaskStatusResponse() {
    return readJsonFromFile("TaskStatusSuccessResponse.json");
  }

  public static String getMultipleSuccessTaskStatusResponse() {
    return readJsonFromFile("MultipleTaskStatusSuccessResponse.json");
  }

  public static String getSuccessJobPostingStatusResponse() {
    return readJsonFromFile("JobPostingStatusSuccessResponse.json");
  }

  public static String getMultipleSuccessJobPostingStatusResponse() {
    return readJsonFromFile("MultipleJobPostingStatusSuccessResponse.json");
  }

  public static String getSuccessP4ProvisionCustomerHiringContractsResponse(){
    return readJsonFromFile("P4PProvisionCustomerHiringContractsSuccessResponse.json");
  }

  public static String getSuccessP4PJobPerformanceReportsByIdsResponse(){
    return readJsonFromFile("P4PJobPerformanceReportsByIdsSuccessResponse.json");
  }

  public static String getSuccessP4PJobPerformanceReportsByDateResponse(){
    return readJsonFromFile("P4PJobPerformanceReportsByDateSuccessResponse.json");
  }

  public static String getSuccessP4PPartnerBudgetReportResponse(){
    return readJsonFromFile("P4PPartnerBudgetReportResponse.json");
  }

  public static String getCreateApplicationSuccessResponse(){
    return readJsonFromFile("CreateApplicationSuccessResponse.json");
  }

  public static String getApplicationSuccessResponse(){
    return readJsonFromFile("GetApplicationSuccessResponse.json");
  }

  public static String getTokenSuccessResponse() {
    return readJsonFromFile("TokenSuccessResponse.json");
  }

  public static String getNewTokenSuccessResponse() {
    return readJsonFromFile("NewTokenSuccessResponse.json");
  }

  private static String readJsonFromFile(String filePath) {
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
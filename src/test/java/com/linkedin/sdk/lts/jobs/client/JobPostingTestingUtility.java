package com.linkedin.sdk.lts.jobs.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JobPostingTestingUtility {

  public static String getSuccessJobPostingResponse(){
    return readJsonFromFile("JobPostingSuccessResponse.txt");
  }

  public static String getSuccessTaskStatusResponse() {
    return readJsonFromFile("TaskStatusSuccessResponse.txt");
  }

  public static String getMultipleSuccessTaskStatusResponse() {
    return readJsonFromFile("MultipleTaskStatusSuccessResponse.txt");
  }

  public static String getSuccessJobPostingStatusResponse() {
    return readJsonFromFile("JobPostingStatusSuccessResponse.txt");
  }

  public static String getMultipleSuccessJobPostingStatusResponse() {
    return readJsonFromFile("MultipleJobPostingStatusSuccessResponse.txt");
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
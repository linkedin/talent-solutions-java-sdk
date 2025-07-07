package com.linkedin.sdk.lts.jobs.model.request.provisioning;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a request to update an existing developer application.
 * This request includes the unique identifier of the application and
 * a list of authorized JavaScript SDK domain names.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequest {

  /**
   * The unique identifier of the application to be updated.
   */
  private String developerApplicationUrn;

  /**
   * List of authorized JavaScript SDK domain names.
   */
  private List<String> validJsSdkDomains;
}

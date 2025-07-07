package com.linkedin.sdk.lts.jobs.model.request.provisioning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a request to retrieve a child application by its unique foreign ID.
 * This request is used to fetch details of a specific child application created
 * under a parent application in the LinkedIn Job Posting API.
 *
 * <p>It contains a single field representing the unique foreign ID of the child application.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationRequest {

  /**
   * Unique ID of the child application supplied during the creation of the child application.
  */
  private String uniqueForeignId;

}

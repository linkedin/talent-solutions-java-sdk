package com.linkedin.sdk.lts.api.model.request.provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for updating an application's configuration.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequestInternal {

  @JsonProperty("patch")
  private PatchOperations patch;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PatchOperations {
    @JsonProperty("$set")
    private ApplicationUpdate set;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ApplicationUpdate {
    @JsonProperty("validJsSdkDomains")
    private List<String> validJsSdkDomains;
  }


  /**
   * Converts an UpdateApplicationRequest to UpdateApplicationRequestInternal.
   * @param request the UpdateApplicationRequest to convert.
   * @return UpdateApplicationRequestInternal instance
   */
  public static UpdateApplicationRequestInternal fromUpdateRequest(UpdateApplicationRequest request) {
    if (request == null) {
      return null;
    }

    return UpdateApplicationRequestInternal.builder()
        .patch(PatchOperations.builder()
            .set(ApplicationUpdate.builder()
                .validJsSdkDomains(request.getValidJsSdkDomains())
                .build())
            .build())
        .build();
  }
}
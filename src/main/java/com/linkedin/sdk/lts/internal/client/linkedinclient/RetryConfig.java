package com.linkedin.sdk.lts.internal.client.linkedinclient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetryConfig {
  @Builder.Default
  private int maxRetries = 3;

  @Builder.Default
  private long initialBackoffMillis = 1000;

  @Builder.Default
  private double backoffMultiplier = 2.0;

  @Builder.Default
  private long maxBackoffMillis = 10000;
}

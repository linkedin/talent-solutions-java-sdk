package com.linkedin.sdk.lts.api.client;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LinkedInClientFactoryTest {

  private static final String TEST_CLIENT_ID = "test-client-id";
  private static final String TEST_CLIENT_SECRET = "test-client-secret";
  private static final String TEST_PARENT_CLIENT_ID = "test-parent-client-id";
  private static final String ALTERNATE_CLIENT_ID = "alternate-client-id";
  private static final String ALTERNATE_CLIENT_SECRET = "alternate-client-secret";

  @Test
  public void testGetInstanceShouldReturnSameInstance() {
    LinkedInClientFactory instance1 = LinkedInClientFactory.getInstance();
    LinkedInClientFactory instance2 = LinkedInClientFactory.getInstance();

    assertNotNull(instance1);
    assertSame(instance1, instance2, "getInstance should return the same instance");
  }

  @Test
  public void testGetJobPostingClientShouldReturnDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    JobPostingClient client1 = factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
    JobPostingClient client2 = factory.getJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);

    assertNotSame(client1, client2, "Different credentials should return different client instances");
  }

  @Test
  public void testGetP4PJobPostingClientShouldReturnDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    P4PJobPostingClient client1 = factory.getP4PJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
    P4PJobPostingClient client2 = factory.getP4PJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);

    assertNotSame(client1, client2, "Different credentials should return different client instances");
  }

  @Test
  public void testGetApplyConnectJobPostingClientShouldReturnDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    ApplyConnectJobPostingClient
        client1 = factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
    ApplyConnectJobPostingClient
        client2 = factory.getApplyConnectJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);

    assertNotSame(client1, client2, "Different credentials should return different client instances");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingClientWhenEmptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getJobPostingClient("", TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetJobPostingClientWhenNullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getJobPostingClient(TEST_CLIENT_ID, null, TEST_PARENT_CLIENT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PJobPostingClientWhenEmptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getP4PJobPostingClient("", TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetP4PJobPostingClientWhenNullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getP4PJobPostingClient(TEST_CLIENT_ID, null, TEST_PARENT_CLIENT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetApplyConnectJobPostingClientWhenEmptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getApplyConnectJobPostingClient("", TEST_CLIENT_SECRET, TEST_PARENT_CLIENT_ID);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetApplyConnectJobPostingClientWhenNullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, null, TEST_PARENT_CLIENT_ID);
  }
}
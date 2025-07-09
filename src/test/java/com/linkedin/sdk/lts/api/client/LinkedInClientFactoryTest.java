package com.linkedin.sdk.lts.api.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedInClientFactoryTest {

  private static final String TEST_CLIENT_ID = "test-client-id";
  private static final String TEST_CLIENT_SECRET = "test-client-secret";
  private static final String ALTERNATE_CLIENT_ID = "alternate-client-id";
  private static final String ALTERNATE_CLIENT_SECRET = "alternate-client-secret";

  @Test
  public void testGetInstance_returnsSingletonInstance() {
    LinkedInClientFactory instance1 = LinkedInClientFactory.getInstance();
    LinkedInClientFactory instance2 = LinkedInClientFactory.getInstance();

    assertNotNull(instance1);
    assertSame("getInstance should return the same instance", instance1, instance2);
  }

  @Test
  public void testGetJobPostingClient_returnsSameInstanceForSameCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    JobPostingClient client1 = factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    JobPostingClient client2 = factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);

    assertNotNull("JobPostingClient should not be null", client1);
    assertNotNull("JobPostingClient should not be null", client2);
    assertSame("Same credentials should return same client instance", client1, client2);
  }

  @Test
  public void testGetP4PJobPostingClient_returnsSameInstanceForSameCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    P4PJobPostingClient client1 = factory.getP4PJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    P4PJobPostingClient client2 = factory.getP4PJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);

    assertNotNull("P4PJobPostingClient should not be null", client1);
    assertNotNull("P4PJobPostingClient should not be null", client2);
    assertSame("Same credentials should return same client instance", client1, client2);
  }

  @Test
  public void testGetApplyConnectJobPostingClient_returnsSameInstanceForSameCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    ApplyConnectJobPostingClient
        client1 = factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    ApplyConnectJobPostingClient
        client2 = factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);

    assertNotNull("ApplyConnectJobPostingClient should not be null", client1);
    assertNotNull("ApplyConnectJobPostingClient should not be null", client2);
    assertSame("Same credentials should return same client instance", client1, client2);
  }

  @Test
  public void testGetJobPostingClient_returnsDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    JobPostingClient client1 = factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    JobPostingClient client2 = factory.getJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET);

    assertNotSame("Different credentials should return different client instances", client1, client2);
  }

  @Test
  public void testGetP4PJobPostingClient_returnsDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    P4PJobPostingClient client1 = factory.getP4PJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    P4PJobPostingClient client2 = factory.getP4PJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET);

    assertNotSame("Different credentials should return different client instances", client1, client2);
  }

  @Test
  public void testGetApplyConnectJobPostingClient_returnsDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    ApplyConnectJobPostingClient
        client1 = factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    ApplyConnectJobPostingClient
        client2 = factory.getApplyConnectJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET);

    assertNotSame("Different credentials should return different client instances", client1, client2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetJobPostingClient_emptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getJobPostingClient("", TEST_CLIENT_SECRET);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetJobPostingClient_nullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getJobPostingClient(TEST_CLIENT_ID, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetP4PJobPostingClient_emptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getP4PJobPostingClient("", TEST_CLIENT_SECRET);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetP4PJobPostingClient_nullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getP4PJobPostingClient(TEST_CLIENT_ID, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetApplyConnectJobPostingClient_emptyClientId() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getApplyConnectJobPostingClient("", TEST_CLIENT_SECRET);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetApplyConnectJobPostingClient_nullClientSecret() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.getApplyConnectJobPostingClient(TEST_CLIENT_ID, null);
  }
}
package com.linkedin.sdk.lts.jobs.client;

import com.linkedin.sdk.lts.jobs.auth.OAuth2Config;
import org.junit.After;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class LinkedInClientFactoryTest {

  private static final String TEST_CLIENT_ID = "test-client-id";
  private static final String TEST_CLIENT_SECRET = "test-client-secret";
  private static final String ALTERNATE_CLIENT_ID = "alternate-client-id";
  private static final String ALTERNATE_CLIENT_SECRET = "alternate-client-secret";

  @After
  public void tearDown() throws Exception {
    // Clear the cache after each test
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();
    factory.clearInstances();
  }

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
  public void testGetJobPostingClient_returnsDifferentInstancesForDifferentCredentials() {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    JobPostingClient client1 = factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
    JobPostingClient client2 = factory.getJobPostingClient(ALTERNATE_CLIENT_ID, ALTERNATE_CLIENT_SECRET);

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

  @Test
  public void testClearInstances() throws Exception {
    LinkedInClientFactory factory = LinkedInClientFactory.getInstance();

    // Create a client to populate the cache
    factory.getJobPostingClient(TEST_CLIENT_ID, TEST_CLIENT_SECRET);

    // Verify cache has an entry
    int sizeBefore = getJobPostingClientInstancesSize(factory);
    assertTrue("Cache should contain at least one entry", sizeBefore > 0);

    // Clear the cache
    factory.clearInstances();

    // Verify cache is empty
    int sizeAfter = getJobPostingClientInstancesSize(factory);
    assertEquals("Cache should be empty after clearing", 0, sizeAfter);
  }

  // Helper method to access the private jobPostingClientInstances field for verification
  private int getJobPostingClientInstancesSize(LinkedInClientFactory factory) throws Exception {
    Field field = JobPostingClient.class.getDeclaredField("INSTANCES");
    field.setAccessible(true);
    ConcurrentHashMap<OAuth2Config, JobPostingClient> map =
        (ConcurrentHashMap<OAuth2Config, JobPostingClient>) field.get(factory);
    return map.size();
  }
}
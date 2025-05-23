package com.linkedin.sdk.lts.jobs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.sdk.lts.jobs.auth.TokenInfo;
import org.junit.Test;
import org.mockito.MockedConstruction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ObjectMapperUtilTest {

    @Test
    public void testToJson_ValidObject_ReturnsJsonString() {
      TokenInfo tokenInfo = new TokenInfo("abc123", new Long(3600));
      String json = ObjectMapperUtil.toJson(tokenInfo);
      assertNotNull(json);
      assertTrue(json.contains("abc123"));
      assertTrue(json.contains("expires_in"));
    }

  @Test
  public void testToJson_ThrowsRuntimeException_WhenJsonProcessingExceptionOccurs() {
    try (MockedConstruction<ObjectMapper> mockedConstruction = mockConstruction(ObjectMapper.class,
        (mock, context) -> {
          // Configure the mock ObjectMapper to throw JsonProcessingException
          when(mock.writeValueAsString(any()))
              .thenThrow(new JsonProcessingException("Mock serialization error") {});
        })) {

      // Test that RuntimeException is thrown when ObjectMapper fails
      RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        ObjectMapperUtil.toJson("test");
      });

      assertEquals("Failed to serialize object to JSON", exception.getMessage());
      assertEquals("Mock serialization error", exception.getCause().getMessage());
    }
  }

    @Test
    public void testFromJson_ValidJson_ReturnsObject() {
      String json = "{\"access_token\":\"xyz789\",\"expires_in\":1800}";
      TokenInfo tokenInfo = ObjectMapperUtil.fromJson(json, TokenInfo.class);
      assertNotNull(tokenInfo);
      assertEquals("xyz789", tokenInfo.getAccessToken());
      assertEquals(new Long(1800), tokenInfo.getExpiresIn());
    }

    @Test(expected = RuntimeException.class)
    public void testFromJson_InvalidJson_ThrowsException() {
      String invalidJson = "{access_token:}";
      ObjectMapperUtil.fromJson(invalidJson, TokenInfo.class);
    }
}

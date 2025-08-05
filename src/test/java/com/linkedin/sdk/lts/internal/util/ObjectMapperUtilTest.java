package com.linkedin.sdk.lts.internal.util;

import com.linkedin.sdk.lts.internal.auth.TokenInfo;
import com.linkedin.sdk.lts.api.exception.JsonDeserializationException;
import com.linkedin.sdk.lts.api.exception.JsonSerializationException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class ObjectMapperUtilTest {

    @Test
    public void testToJsonValidObjectShouldReturnJsonString() throws JsonSerializationException {
      TokenInfo tokenInfo = new TokenInfo("abc123", new Long(3600));
      String json = ObjectMapperUtil.toJson(tokenInfo);
      assertNotNull(json);
      assertTrue(json.contains("abc123"));
      assertTrue(json.contains("expires_in"));
    }

    @Test
    public void testFromJsonWithValidJsonShouldReturnsObject() throws JsonDeserializationException {
      String json = "{\"access_token\":\"xyz789\",\"expires_in\":1800}";
      TokenInfo tokenInfo = ObjectMapperUtil.fromJson(json, TokenInfo.class);
      assertNotNull(tokenInfo);
      assertEquals("xyz789", tokenInfo.getAccessToken());
      assertEquals(new Long(1800), tokenInfo.getExpiresIn());
    }

    @Test(expectedExceptions = JsonDeserializationException.class)
    public void testFromJsonWithInvalidJsonShouldThrowsException() throws JsonDeserializationException {
      String invalidJson = "{access_token:}";
      ObjectMapperUtil.fromJson(invalidJson, TokenInfo.class);
    }
}

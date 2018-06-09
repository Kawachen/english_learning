package DatamodelTests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessTokenTest {

    private int testId = 42;
    private String testAccessString = "blablub";

    private int testId2 = 13;

    @Test
    public void testCreateAccessTokenObject() {
        AccessToken accessToken = new AccessToken(testId, testAccessString);
        assertEquals(testId, accessToken.getId());
        assertEquals(testAccessString, accessToken.getAccessString());
    }

    @Test
    public void testSetterOfAccessTokenObject() {
        AccessToken accessToken = new AccessToken(testId, testAccessString);
        accessToken.setId(testId2);
        assertEquals(testId2, accessToken.getId());
    }
}

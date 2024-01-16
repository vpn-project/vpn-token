package com.nesterrovv.vpntoken.vpnaccount.entity;

import com.nesterrovv.vpntoken.entity.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    private Token token1;
    private Token token2;

    @BeforeEach
    void setUp() {
        token1 = new Token();
        token1.setId(1);
        token1.setToken("token1");

        token2 = new Token();
        token2.setId(2);
        token2.setToken("token2");
    }

    @Test
    void testSetId() {
        // Arrange
        Token token = new Token();
        Integer expectedId = 1;

        // Act
        token.setId(expectedId);

        // Assert
        assertEquals(expectedId, token.getId());
    }

    @Test
    void testGetToken() {
        // Arrange
        Token token = new Token();
        String expectedToken = "test_token";
        // Act
        token.setToken(expectedToken);

        // Assert
        assertEquals(expectedToken, token.getToken());
    }

    @Test
    void testSetToken() {
        // Arrange
        Token token = new Token();
        String expectedToken = "test_token";
        // Act
        token.setToken(expectedToken);

        // Assert
        assertEquals(expectedToken, token.getToken());
    }

    @Test
    void testEquals() {
        // Arrange
        Token token1 = new Token();
        token1.setId(1);
        token1.setToken("test_token");

        Token token2 = new Token();
        token2.setId(1);
        token2.setToken("test_token");

        Token token3 = new Token();
        token3.setId(2);
        token3.setToken("different_token");

        // Act & Assert
        assertTrue(token1.equals(token2));
        assertFalse(token1.equals(token3));
    }

    @Test
    void testHashCode() {
        // Arrange
        Token token1 = new Token();
        token1.setId(1);
        token1.setToken("test_token");

        Token token2 = new Token();
        token2.setId(1);
        token2.setToken("test_token");

        Token token3 = new Token();
        token3.setId(2);
        token3.setToken("different_token");

        // Act & Assert
        assertEquals(token1.hashCode(), token2.hashCode());
        assertNotEquals(token1.hashCode(), token3.hashCode());
    }

}

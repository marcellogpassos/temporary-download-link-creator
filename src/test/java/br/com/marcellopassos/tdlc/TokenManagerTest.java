package br.com.marcellopassos.tdlc;

import br.com.marcellopassos.tdlc.exceptions.TokenManagerException;
import br.com.marcellopassos.tdlc.helpers.DefaultCryptographyHelper;
import br.com.marcellopassos.tdlc.helpers.DefaultEncodingHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenManagerTest {

    private final ITokenManager<String, Long> tokenManager;

    public TokenManagerTest() {
        this.tokenManager = new TokenManager<>(5000L,
                new DefaultCryptographyHelper("WnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/"),
                new DefaultEncodingHelper());
    }

    @Test
    public void generate() {
        Document receipt = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/receipt.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload<>(receipt, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);
        assertNotNull(token);
    }

    @Test
    public void evaluate() {
        Document receipt = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/receipt.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload<>(receipt, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);
        TokenPayload<String, Long> evaluatedTokenPayload = this.tokenManager.evaluate(token, johnDoe);
        assertNotNull(evaluatedTokenPayload);
        assertEquals(receipt.getClass(), evaluatedTokenPayload.getResourceClass());
        assertEquals(receipt.getId(), evaluatedTokenPayload.getResourceId());
        assertTrue(evaluatedTokenPayload.getUserId().isPresent());
        assertEquals(johnDoe.getId(), evaluatedTokenPayload.getUserId().get());
    }

    @Test
    public void evaluate_throwExpiredToken() throws InterruptedException {
        Document receipt = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/receipt.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload<>(receipt, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);

        Thread.sleep(5000L);

        assertThrows(TokenManagerException.class, () -> this.tokenManager.evaluate(token));
    }

    @Test
    public void evaluate_throwAccessDenied() {
        Document receipt = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/receipt.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload<>(receipt, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);
        User jeanPaul = new User(2L, "Jean Paul");
        assertThrows(TokenManagerException.class, () -> this.tokenManager.evaluate(token, jeanPaul));
    }

}

package br.com.marcellopassos.tdlc;

import br.com.marcellopassos.tdlc.exceptions.TokenManagerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenManagerTest {

    private final ITokenManager<String, Long> tokenManager;

    public TokenManagerTest() {
        this.tokenManager = new TokenManager("WnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/", 10000L);
    }

    @Test
    public void generate() {
        Document recipe = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/recipe.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload(recipe, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);
        assertNotNull(token);
    }

    @Test
    public void evaluate() {
        Document recipe = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/recipe.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload(recipe, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);
        this.tokenManager.evaluate(token, johnDoe);
    }

    @Test
    public void evaluate_throwExpiredToken() throws InterruptedException {
        Document recipe = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/recipe.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload(recipe, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);

        Thread.sleep(10000L);

        assertThrows(TokenManagerException.class, () -> this.tokenManager.evaluate(token));
    }

    @Test
    public void evaluate_throwAccessDenied() {
        Document recipe = new Document("e10adc3949ba59abbe56e057f20f883e", 10L, "/home/john/recipe.pdf",
                "application/pdf");
        User johnDoe = new User(1L, "John Doe");
        TokenPayload<String, Long> tokenPayload = new TokenPayload(recipe, johnDoe);
        String token = this.tokenManager.generate(tokenPayload);

        User jeanPaul = new User(2L, "Jean Paul");

        assertThrows(TokenManagerException.class, () -> this.tokenManager.evaluate(token, jeanPaul));
    }


}

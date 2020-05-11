package br.com.marcellopassos.tdlc.exceptions;

public class TokenManagerException extends RuntimeException {

    public TokenManagerException(String message) {
        super(message);
    }

    public TokenManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}

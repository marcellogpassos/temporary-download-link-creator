package br.com.marcellopassos.tdlc.exceptions;

public class CryptographyException extends RuntimeException {

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }
}

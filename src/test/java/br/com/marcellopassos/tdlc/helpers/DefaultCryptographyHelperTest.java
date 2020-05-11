package br.com.marcellopassos.tdlc.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultCryptographyHelperTest {

    private final ICryptographyHelper cryptographyHelper;

    public DefaultCryptographyHelperTest() {
        this.cryptographyHelper = new DefaultCryptographyHelper();
    }

    @Test
    public void test() {
        String originalMessage = "This is a secret message";
        byte[] encryptionKeyBytes = "WnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/".getBytes();
        byte[] encryptedMessageBytes = this.cryptographyHelper.encrypt(originalMessage.getBytes(), encryptionKeyBytes);
        byte[] decryptedMessageBytes = this.cryptographyHelper.decrypt(encryptedMessageBytes, encryptionKeyBytes);
        assertEquals(originalMessage, new String(decryptedMessageBytes));
    }

}

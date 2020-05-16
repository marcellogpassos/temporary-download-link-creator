package br.com.marcellopassos.tdlc.helpers;

import br.com.marcellopassos.tdlc.exceptions.CryptographyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultCryptographyHelperTest {

    @Test
    public void test() {
        final ICryptographyHelper cryptographyHelper =
                new DefaultCryptographyHelper("WnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/");
        String originalMessage = "This is a secret message";
        byte[] encryptedMessageBytes = cryptographyHelper.encrypt(originalMessage.getBytes());
        byte[] decryptedMessageBytes = cryptographyHelper.decrypt(encryptedMessageBytes);
        assertEquals(originalMessage, new String(decryptedMessageBytes));
    }

    @Test
    public void testThrowsCryptographyException() {
        final ICryptographyHelper cryptographyHelper = new DefaultCryptographyHelper(null);
        assertThrows(CryptographyException.class, () -> {
            String originalMessage = "This is a secret message";
            cryptographyHelper.decrypt(cryptographyHelper.encrypt(originalMessage.getBytes()));
        });
    }

}

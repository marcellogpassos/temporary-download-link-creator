package br.com.marcellopassos.tdlc.helpers;

import br.com.marcellopassos.tdlc.exceptions.CryptographyException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DefaultCryptographyHelper implements ICryptographyHelper {

    public static final String DEFAULT_CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static final String ALGORITHM = "AES";

    private final String secretKey;

    public DefaultCryptographyHelper(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public byte[] encrypt(byte[] plainMessageBytes) {
        try {
            return this.getCipher(Cipher.ENCRYPT_MODE).doFinal(plainMessageBytes);
        } catch (Exception ex) {
            throw new CryptographyException("Falha ao tentar criptografar o token", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] encryptedMessage) {
        try {
            return this.getCipher(Cipher.DECRYPT_MODE).doFinal(encryptedMessage);
        } catch (Exception ex) {
            throw new CryptographyException("Falha ao tentar descriptografar o token", ex);
        }
    }

    private Cipher getCipher(int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_TRANSFORMATION);
        cipher.init(mode, new SecretKeySpec(this.secretKey.getBytes(), ALGORITHM));
        return cipher;
    }
}

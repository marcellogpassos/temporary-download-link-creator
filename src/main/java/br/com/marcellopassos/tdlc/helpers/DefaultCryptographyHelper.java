package br.com.marcellopassos.tdlc.helpers;

import br.com.marcellopassos.tdlc.exceptions.CryptographyException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DefaultCryptographyHelper implements ICryptographyHelper {

    public static final String DEFAULT_CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static final String ALGORITHM = "AES";

    @Override
    public byte[] encrypt(byte[] plainMessageBytes, byte[] keyBytes) {
        try {
            return this.getCipher(keyBytes, Cipher.ENCRYPT_MODE).doFinal(plainMessageBytes);
        } catch (Exception ex) {
            throw new CryptographyException("Falha ao tentar criptografar o token", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] encryptedMessage, byte[] keyBytes) {
        try {
            return this.getCipher(keyBytes, Cipher.DECRYPT_MODE).doFinal(encryptedMessage);
        } catch (Exception ex) {
            throw new CryptographyException("Falha ao tentar descriptografar o token", ex);
        }
    }

    private Cipher getCipher(byte[] keyBytes, int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(mode, secretKey);
        return cipher;
    }
}

package br.com.marcellopassos.tdlc.helpers;

public interface ICryptographyHelper {

    byte[] encrypt(byte[] plainMessage, byte[] keyBytes);

    byte[] decrypt(byte[] encryptedMessage, byte[] keyBytes);

}

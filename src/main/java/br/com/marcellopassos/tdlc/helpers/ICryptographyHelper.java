package br.com.marcellopassos.tdlc.helpers;

public interface ICryptographyHelper {

    byte[] encrypt(byte[] plainMessage);

    byte[] decrypt(byte[] encryptedMessage);

}

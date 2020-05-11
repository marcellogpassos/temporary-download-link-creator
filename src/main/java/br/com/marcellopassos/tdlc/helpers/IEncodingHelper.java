package br.com.marcellopassos.tdlc.helpers;

public interface IEncodingHelper {

    String encode(byte[] messageBytes);

    byte[] decode(String encodedMessage);

}

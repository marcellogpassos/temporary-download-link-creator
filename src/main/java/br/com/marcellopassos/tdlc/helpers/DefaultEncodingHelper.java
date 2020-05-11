package br.com.marcellopassos.tdlc.helpers;

import java.util.Base64;

public class DefaultEncodingHelper implements IEncodingHelper {

    @Override
    public String encode(byte[] messageBytes) {
        return Base64.getEncoder().encodeToString(messageBytes);
    }

    @Override
    public byte[] decode(String encodedMessage) {
        return Base64.getDecoder().decode(encodedMessage);
    }
}

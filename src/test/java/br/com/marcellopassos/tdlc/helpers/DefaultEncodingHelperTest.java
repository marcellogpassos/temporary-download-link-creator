package br.com.marcellopassos.tdlc.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultEncodingHelperTest {

    private final IEncodingHelper encodingHelper;

    public DefaultEncodingHelperTest() {
        this.encodingHelper = new DefaultEncodingHelper();
    }

    @Test
    public void encode() {
        String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        String encoded = this.encodingHelper.encode(message.getBytes());
        assertEquals("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4=", encoded);
    }

    @Test
    public void decode() {
        String encoded = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4=";
        byte[] messageBytes = this.encodingHelper.decode(encoded);
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", new String(messageBytes));
    }
}

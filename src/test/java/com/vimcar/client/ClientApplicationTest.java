package com.vimcar.client;

import com.vimcar.client.sender.HttpSender;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClientApplicationTest {

    private ClientApplication application = new ClientApplication();

    private HttpSender sender = mock(HttpSender.class);

    private Reader reader = new StringReader("apple\noranges\nending");

    @Test
    void shouldStartProcessingSuccessfully() throws IOException {
        boolean result = application.startProcessing(sender, new BufferedReader(reader));
        verify(sender, times(1)).send("apple");
        verify(sender, times(1)).send("oranges");
        verify(sender, times(1)).send("ending");
        assertTrue(result);
    }
}

package com.vimcar.client.sender;

import com.vimcar.client.configuration.CurrentThreadExecutor;
import com.vimcar.client.server.ServerClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;


class HttpSenderServiceTest {
    private ServerClient server = mock(ServerClient.class);
    private HttpSender sender = new HttpSender(new CurrentThreadExecutor(), server);


    @BeforeEach
    void setUp() throws IOException, InterruptedException {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSendMessageSuccessfully() {
        String message = "Hello";
        sender.send(message);
        verify(server, times(1)).sendMessage(message);
    }
}

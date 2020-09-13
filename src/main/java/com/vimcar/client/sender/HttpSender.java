package com.vimcar.client.sender;

import com.vimcar.client.server.ServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
public class HttpSender implements Sender {

    private Executor executor;
    private ServerClient server;

    @Autowired
    public HttpSender(Executor executor,
                      ServerClient server
    ) {
        this.executor = executor;
        this.server = server;
    }

    @Override
    public void send(String message) {
        executor.execute(() ->
                server.sendMessage(message)
        );
    }

}

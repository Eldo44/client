package com.vimcar.client;

import com.vimcar.client.sender.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);

        Sender senderInstance = context.getBean(Sender.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            startProcessing(senderInstance, reader);
        }
    }

    static boolean startProcessing(Sender sender, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            sender.send(line);
        }
        return true;
    }
}

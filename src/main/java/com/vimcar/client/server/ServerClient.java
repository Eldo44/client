package com.vimcar.client.server;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import retrofit2.Response;

import java.io.IOException;

public class ServerClient {

    private ExternalService service;

    public ServerClient(ExternalService service) {
        this.service = service;
    }

    public boolean sendMessage(String message) {
        try {
            Response response = service.sendMessage(message)
                    .execute();
            if (!response.isSuccessful()) {
                // record status code
                System.err.println(message + " Not successful ");
                return false;
            }
            return true;
        } catch (IOException exception) {
            // record Failure
            System.err.println(message + " Not successful exception " + exception.getMessage());
        } catch (CallNotPermittedException exception) {
            // record Failure
            System.err.println(message + "Not successful exception, circuit breaker is open " + exception.getMessage());
        } catch (Exception exception) {
            // record Failure
            System.err.println(message + "Unknown exception " + exception.getMessage());
        }
        return false;
    }
}


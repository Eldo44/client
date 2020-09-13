package com.vimcar.client.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ExternalService {
    @Headers({"Content-Type: text/plain"})
    @POST("/message")
    Call<Void> sendMessage(@Body String message);
}

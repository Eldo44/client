package com.vimcar.client.configuration;

import com.vimcar.client.server.ExternalService;
import com.vimcar.client.server.ServerClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

@Configuration
public class ExternalServicesConfig {

    @Autowired
    ExternalServicesConfig(@Value("${app.server.uri}") String host) {
        this.host = host;
    }

    private String host;

    private CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .ringBufferSizeInClosedState(10)
            .ringBufferSizeInHalfOpenState(10)
            .build();

    private CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

    public CircuitBreaker circuitBreaker() {
        return registry.circuitBreaker("external-server");
    }

    final long TIMEOUT = 5000; // ms

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    @Bean
    public ExternalService getServer() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreaker()))
                .baseUrl(host)
                .addConverterFactory(new StringConverterFactory())
                .client(client)
                .build()
                .create(ExternalService.class);
    }

    @Bean
    public ServerClient getServerClient(@Autowired ExternalService service) {
        return new ServerClient(service);
    }
}
